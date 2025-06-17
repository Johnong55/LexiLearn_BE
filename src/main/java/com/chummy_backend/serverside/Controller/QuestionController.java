package com.chummy_backend.serverside.Controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chummy_backend.serverside.DTO.request.QuestionRequest;
import com.chummy_backend.serverside.DTO.response.QuestionResponse;
import com.chummy_backend.serverside.Exception.BadRequestException;
import com.chummy_backend.serverside.Exception.ResourceNotFoundException;
import com.chummy_backend.serverside.Model.examination.question;
import com.chummy_backend.serverside.Service.questionService;

@RestController
@RequestMapping("/api/questions")
public class QuestionController {
    @Autowired
    private questionService questionService;

    @GetMapping
    public ResponseEntity<List<QuestionResponse>> getAllQuestions() {
        try {
            List<QuestionResponse> result = questionService.findAll().stream().map(this::toResponse).collect(Collectors.toList());
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            throw new BadRequestException("Failed to get questions");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuestionResponse> getQuestion(@PathVariable Long id) {
        try {
            question q = questionService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Question not found"));
            return ResponseEntity.ok(toResponse(q));
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new BadRequestException("Failed to get question");
        }
    }

    @PostMapping
    public ResponseEntity<QuestionResponse> createQuestion(@RequestBody QuestionRequest request) {
        try {
            question q = new question();
            q.setContent(request.getContent());
            // Set vocabulary if needed
            QuestionResponse res = toResponse(questionService.save(q));
            return ResponseEntity.ok(res);
        } catch (Exception e) {
            throw new BadRequestException("Failed to create question");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteQuestion(@PathVariable Long id) {
        try {
            questionService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            throw new BadRequestException("Failed to delete question");
        }
    }

    private QuestionResponse toResponse(question q) {
        QuestionResponse res = new QuestionResponse();
        res.setId(q.getId());
        res.setContent(q.getContent());
        if (q.getVocabulary() != null) {
            res.setVocabularyId(q.getVocabulary().getId());
            res.setVocabularyWord(q.getVocabulary().getWord());
        }
        return res;
    }
}
