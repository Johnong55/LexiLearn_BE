package com.chummy_backend.serverside.Controller;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chummy_backend.serverside.DTO.request.QuestionGenerationRequest;
import com.chummy_backend.serverside.DTO.response.QuestionResponse;
import com.chummy_backend.serverside.Service.QuestionService;

import lombok.RequiredArgsConstructor;
@RestController
@RequestMapping("/api/questions")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class QuestionController {
    private final QuestionService questionService;
    @PostMapping("/generate")
    public ResponseEntity<QuestionResponse> generateQuestion(
            @RequestBody QuestionGenerationRequest request) {
        QuestionResponse response = questionService.generateAndSaveQuestion(request);
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/generate-batch")
    public ResponseEntity<List<QuestionResponse>> generateMultipleQuestions(
            @RequestBody List<QuestionGenerationRequest> requests) {
        List<QuestionResponse> responses = questionService.generateMultipleQuestions(requests);
        return ResponseEntity.ok(responses);
    }
    
    @GetMapping("/vocabulary/{vocabularyId}")
    public ResponseEntity<List<QuestionResponse>> getQuestionsByVocabulary(
            @PathVariable Long vocabularyId) {
        List<QuestionResponse> questions = questionService.getQuestionsByVocabulary(vocabularyId);
        return ResponseEntity.ok(questions);
    }
    
    @GetMapping("/word/{word}")
    public ResponseEntity<List<QuestionResponse>> getQuestionsByWord(
            @PathVariable String word) {
        List<QuestionResponse> questions = questionService.getQuestionsByWord(word);
        return ResponseEntity.ok(questions);
    }
    
    @DeleteMapping("/{questionId}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable Long questionId) {
        questionService.deleteQuestion(questionId);
        return ResponseEntity.noContent().build();
    }
}