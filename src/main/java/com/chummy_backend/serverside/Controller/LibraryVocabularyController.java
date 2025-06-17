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

import com.chummy_backend.serverside.DTO.request.LibraryVocabularyRequest;
import com.chummy_backend.serverside.DTO.response.LibraryVocabularyResponse;
import com.chummy_backend.serverside.Exception.BadRequestException;
import com.chummy_backend.serverside.Exception.ResourceNotFoundException;
import com.chummy_backend.serverside.Model.examination.library_vocabulary;
import com.chummy_backend.serverside.Service.library_vocabularyService;

@RestController
@RequestMapping("/api/library-vocabularies")
public class LibraryVocabularyController {
    @Autowired
    private library_vocabularyService libraryVocabularyService;

    @GetMapping
    public ResponseEntity<List<LibraryVocabularyResponse>> getAllLibraryVocabularies() {
        try {
            List<LibraryVocabularyResponse> result = libraryVocabularyService.findAll().stream().map(this::toResponse).collect(Collectors.toList());
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            throw new BadRequestException("Failed to get library vocabularies");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<LibraryVocabularyResponse> getLibraryVocabulary(@PathVariable Long id) {
        try {
            library_vocabulary lv = libraryVocabularyService.findById(id).orElseThrow(() -> new ResourceNotFoundException("LibraryVocabulary not found"));
            return ResponseEntity.ok(toResponse(lv));
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new BadRequestException("Failed to get library vocabulary");
        }
    }

    @PostMapping
    public ResponseEntity<LibraryVocabularyResponse> createLibraryVocabulary(@RequestBody LibraryVocabularyRequest request) {
        try {
            library_vocabulary lv = new library_vocabulary();
            // Set vocabulary and library if needed
            LibraryVocabularyResponse res = toResponse(libraryVocabularyService.save(lv));
            return ResponseEntity.ok(res);
        } catch (Exception e) {
            throw new BadRequestException("Failed to create library vocabulary");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteLibraryVocabulary(@PathVariable Long id) {
        try {
            libraryVocabularyService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            throw new BadRequestException("Failed to delete library vocabulary");
        }
    }

    private LibraryVocabularyResponse toResponse(library_vocabulary lv) {
        LibraryVocabularyResponse res = new LibraryVocabularyResponse();
        res.setId(lv.getId());
        // Set vocabularyId, vocabularyWord, libraryId, libraryName if needed
        return res;
    }
}
