package com.chummy_backend.serverside.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chummy_backend.serverside.DTO.request.QuestionGenerationRequest;
import com.chummy_backend.serverside.DTO.response.GeneratedQuestionResponse;
import com.chummy_backend.serverside.DTO.response.QuestionResponse;
import com.chummy_backend.serverside.DTO.response.VocabularyResponse;
import com.chummy_backend.serverside.Model.examination.Vocabulary;
import com.chummy_backend.serverside.Model.examination.question;
import com.chummy_backend.serverside.Repository.VocabularyRepository;
import com.chummy_backend.serverside.Repository.questionRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional
public class QuestionService {
    
    @Autowired
    private questionRepository questionRepository;
    
    @Autowired
    private VocabularyRepository vocabularyRepository;
    
    // Thay đổi từ DeepSeekAIService sang MistralAIService
    @Autowired
    private MistralAIService mistralAIService;
    
    public QuestionResponse generateAndSaveQuestion(QuestionGenerationRequest request) {
        log.info("🚀 Generating question for word: {} with type: {}", 
                request.getWord(), request.getQuestionType());
        
        // Tìm hoặc tạo vocabulary
        Vocabulary vocabulary = vocabularyRepository.findByWordAndMeaning(
            request.getWord(), request.getMeaning()
        ).orElseGet(() -> createNewVocabulary(request.getWord(), request.getMeaning()));
        
        // Generate câu hỏi bằng Mistral AI
        GeneratedQuestionResponse aiResponse;
        if ("MULTIPLE_CHOICE".equals(request.getQuestionType())) {
            aiResponse = mistralAIService.generateMultipleChoiceQuestion(request);
        } else {
            aiResponse = mistralAIService.generateFillInBlankQuestion(request);
        }
        
        log.info("✅ AI generated question: {}", aiResponse.getContent());
        
        // Tạo và lưu question
        question questions = question.builder()
            .content(aiResponse.getContent())
            .vocabulary(vocabulary)
            .choice(aiResponse.getChoices())
            .build();
        
        question savedQuestion = questionRepository.save(questions);
        
        return mapToQuestionResponse(savedQuestion, aiResponse.getCorrectAnswer(), aiResponse.getExplanation());
    }
    
    public List<QuestionResponse> generateMultipleQuestions(List<QuestionGenerationRequest> requests) {
        log.info("🚀 Generating {} questions", requests.size());
        
        return requests.stream()
            .map(this::generateAndSaveQuestion)
            .collect(Collectors.toList());
    }
    
    public List<QuestionResponse> getQuestionsByVocabulary(Long vocabularyId) {
        List<question> questions = questionRepository.findByVocabularyId(vocabularyId);
        return questions.stream()
            .map(q -> mapToQuestionResponse(q, q.getVocabulary().getWord(), null))
            .collect(Collectors.toList());
    }
    
    public List<QuestionResponse> getQuestionsByWord(String word) {
        List<question> questions = questionRepository.findByVocabularyWord(word);
        return questions.stream()
            .map(q -> mapToQuestionResponse(q, q.getVocabulary().getWord(), null))
            .collect(Collectors.toList());
    }
    
    public void deleteQuestion(Long questionId) {
        questionRepository.deleteById(questionId);
        log.info("🗑️ Deleted question with ID: {}", questionId);
    }
    
    private Vocabulary createNewVocabulary(String word, String meaning) {
        Vocabulary vocabulary = Vocabulary.builder()
            .word(word)
            .meaning(meaning)
            .build();
        
        Vocabulary saved = vocabularyRepository.save(vocabulary);
        log.info("📝 Created new vocabulary: {} - {}", word, meaning);
        return saved;
    }
    
    private QuestionResponse mapToQuestionResponse(question question, String correctAnswer, String explanation) {
        QuestionResponse response = new QuestionResponse();
        response.setId(question.getId());
        response.setContent(question.getContent());
        response.setChoices(question.getChoice());
        response.setCorrectAnswer(correctAnswer);
        response.setQuestionType(question.getChoice() != null && !question.getChoice().isEmpty() 
            ? "MULTIPLE_CHOICE" : "FILL_IN_BLANK");
        
        if (explanation != null) {
            response.setExplanation(explanation);
        }
        
        // Map vocabulary
        VocabularyResponse vocabResponse = new VocabularyResponse();
        vocabResponse.setId(question.getVocabulary().getId());
        vocabResponse.setWord(question.getVocabulary().getWord());
        vocabResponse.setMeaning(question.getVocabulary().getMeaning());
        response.setVocabulary(vocabResponse);
        
        return response;
    }
}