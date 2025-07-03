package com.chummy_backend.serverside.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chummy_backend.serverside.DTO.request.QuestionGenerationRequest;
import com.chummy_backend.serverside.DTO.response.GeneratedQuestionResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MistralAIService {

    @Autowired
    private ChatClient chatClient;

    @Autowired
    private ObjectMapper objectMapper;

    private static final String MULTIPLE_CHOICE_TEMPLATE = """
        Create an English multiple choice question to test vocabulary knowledge.
        
        Word: %s
        Meaning: %s
        Difficulty: %s
        Number of choices: %d
        
        Requirements:
        1. Create an English sentence with clear context, replace the word "%s" with blank "_____"
        2. Create %d choices, including 1 correct answer "%s" and reasonable wrong answers
        3. Wrong answers should be the same word type and similar difficulty level
        4. Provide a brief explanation for the correct answer
        5. Make sure the sentence is grammatically correct and natural
        
        Return the result in JSON format:
        {
            "content": "question with blank",
            "choices": ["choice 1", "choice 2", "choice 3", "choice 4"],
            "correctAnswer": "correct answer",
            "explanation": "explanation",
            "questionType": "MULTIPLE_CHOICE"
        }
        
        IMPORTANT: Return ONLY the JSON, no additional text or formatting.
        """;

    private static final String FILL_IN_BLANK_TEMPLATE = """
        Create an English fill-in-the-blank question.
        
        Word: %s
        Meaning: %s
        Difficulty: %s
        Context: %s
        
        Requirements:
        1. Create an English sentence with appropriate context for the word meaning
        2. Replace the word "%s" with blank "_____"
        3. The sentence should have %s difficulty level
        4. Provide hints if needed
        5. Make sure the sentence is grammatically correct and natural
        
        Return the result in JSON format:
        {
            "content": "question with blank",
            "correctAnswer": "correct answer",
            "explanation": "explanation and hints",
            "questionType": "FILL_IN_BLANK"
        }
        
        IMPORTANT: Return ONLY the JSON, no additional text or formatting.
        """;

    public GeneratedQuestionResponse generateMultipleChoiceQuestion(QuestionGenerationRequest request) {
        String prompt = String.format(MULTIPLE_CHOICE_TEMPLATE,
                request.getWord(),
                request.getMeaning(),
                request.getDifficulty(),
                request.getNumberOfChoices(),
                request.getWord(),
                request.getNumberOfChoices(),
                request.getWord()
        );

        try {
            String response = callMistralAI(prompt);
            return parseJsonResponse(response);
        } catch (Exception e) {
            log.warn("⚠️ Mistral multiple choice fallback: {}", e.getMessage());
            return createFallbackMultipleChoice(request);
        }
    }

    public GeneratedQuestionResponse generateFillInBlankQuestion(QuestionGenerationRequest request) {
        String context = request.getContext() != null ? request.getContext() : "general";

        String prompt = String.format(FILL_IN_BLANK_TEMPLATE,
                request.getWord(),
                request.getMeaning(),
                request.getDifficulty(),
                context,
                request.getWord(),
                request.getDifficulty()
        );

        try {
            String response = callMistralAI(prompt);
            return parseJsonResponse(response);
        } catch (Exception e) {
            log.warn("⚠️ Mistral fill-in-blank fallback: {}", e.getMessage());
            return createFallbackFillInBlank(request);
        }
    }

    private String callMistralAI(String prompt) {
        try {
            log.info("📨 Prompt sent to Mistral (Ollama):\n{}", prompt);

            ChatResponse response = chatClient.prompt()
                    .user(prompt)
                    .call()
                    .chatResponse();

            String content = response.getResult().getOutput().getContent();
            log.info("✅ Mistral response:\n{}", content);

            return content;

        } catch (Exception e) {
            String msg = e.getMessage();
            log.error("❌ Mistral (Ollama) API error: {}", msg);
            
            if (msg != null && msg.contains("connection refused")) {
                throw new RuntimeException("Ollama server is not running. Please start Ollama service.");
            }
            throw new RuntimeException("Mistral call failed", e);
        }
    }

    private GeneratedQuestionResponse parseJsonResponse(String jsonResponse) {
        try {
            // Clean up the response - remove code blocks and extra whitespace
            jsonResponse = jsonResponse.replaceAll("```json", "").replaceAll("```", "").trim();
            
            // Find JSON object in response
            int startIndex = jsonResponse.indexOf("{");
            int endIndex = jsonResponse.lastIndexOf("}") + 1;
            
            if (startIndex != -1 && endIndex > startIndex) {
                jsonResponse = jsonResponse.substring(startIndex, endIndex);
            }

            JsonNode jsonNode = objectMapper.readTree(jsonResponse);

            List<String> choices = new ArrayList<>();
            if (jsonNode.has("choices")) {
                jsonNode.get("choices").forEach(choice -> choices.add(choice.asText()));
            }

            return GeneratedQuestionResponse.builder()
                    .content(jsonNode.get("content").asText())
                    .correctAnswer(jsonNode.get("correctAnswer").asText())
                    .explanation(jsonNode.get("explanation").asText())
                    .questionType(jsonNode.get("questionType").asText())
                    .choices(choices.isEmpty() ? null : choices)
                    .build();

        } catch (Exception e) {
            log.error("❌ JSON parse error: {}", e.getMessage(), e);
            log.error("❌ Raw response: {}", jsonResponse);
            throw new RuntimeException("Failed to parse Mistral response", e);
        }
    }

    private GeneratedQuestionResponse createFallbackMultipleChoice(QuestionGenerationRequest request) {
        String word = request.getWord();
        String meaning = request.getMeaning();
        
        return GeneratedQuestionResponse.builder()
                .content("The student needs to _____ the new vocabulary to improve their English.")
                .choices(Arrays.asList("learn", "forget", "ignore", "avoid"))
                .correctAnswer(word)
                .explanation("The correct answer is '" + word + "' which means: " + meaning)
                .questionType("MULTIPLE_CHOICE")
                .build();
    }

    private GeneratedQuestionResponse createFallbackFillInBlank(QuestionGenerationRequest request) {
        String word = request.getWord();
        String meaning = request.getMeaning();
        
        return GeneratedQuestionResponse.builder()
                .content("Please _____ the correct answer based on the context.")
                .correctAnswer(word)
                .explanation("The word '" + word + "' means: " + meaning)
                .questionType("FILL_IN_BLANK")
                .build();
    }
}