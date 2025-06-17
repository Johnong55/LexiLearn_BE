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

import com.chummy_backend.serverside.DTO.request.VocabularyRequest;
import com.chummy_backend.serverside.DTO.response.VocabularyResponse;
import com.chummy_backend.serverside.Exception.BadRequestException;
import com.chummy_backend.serverside.Exception.ResourceNotFoundException;
import com.chummy_backend.serverside.Model.examination.Vocabulary;
import com.chummy_backend.serverside.Service.VocabularyService;

@RestController
@RequestMapping("/api/vocabularies")
public class VocabularyController {
    @Autowired
    private VocabularyService vocabularyService;

    @GetMapping
    public ResponseEntity<List<VocabularyResponse>> getAllVocabularies() {
        try {
            List<VocabularyResponse> result = vocabularyService.findAll().stream().map(this::toResponse).collect(Collectors.toList());
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            throw new BadRequestException("Failed to get vocabularies");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<VocabularyResponse> getVocabulary(@PathVariable Long id) {
        try {
            Vocabulary v = vocabularyService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Vocabulary not found"));
            return ResponseEntity.ok(toResponse(v));
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new BadRequestException("Failed to get vocabulary");
        }
    }

    @PostMapping
    public ResponseEntity<VocabularyResponse> createVocabulary(@RequestBody VocabularyRequest request) {
        try {
            Vocabulary v = new Vocabulary();
            v.setWord(request.getWord());
            v.setMeaning(request.getMeaning());
            VocabularyResponse res = toResponse(vocabularyService.save(v));
            return ResponseEntity.ok(res);
        } catch (Exception e) {
            throw new BadRequestException("Failed to create vocabulary");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteVocabulary(@PathVariable Long id) {
        try {
            vocabularyService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            throw new BadRequestException("Failed to delete vocabulary");
        }
    }

    private VocabularyResponse toResponse(Vocabulary v) {
        VocabularyResponse res = new VocabularyResponse();
        res.setId(v.getId());
        res.setWord(v.getWord());
        res.setMeaning(v.getMeaning());
        // Set questionIds if needed
        return res;
    }
}
