package com.chummy_backend.serverside.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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

import com.chummy_backend.serverside.DTO.request.LibraryRequest;
import com.chummy_backend.serverside.DTO.response.LibraryResponse;
import com.chummy_backend.serverside.Exception.BadRequestException;
import com.chummy_backend.serverside.Exception.ResourceNotFoundException;
import com.chummy_backend.serverside.Model.examination.Vocabulary;
import com.chummy_backend.serverside.Model.examination.library;
import com.chummy_backend.serverside.Model.examination.library_vocabulary;
import com.chummy_backend.serverside.Model.general.Users;
import com.chummy_backend.serverside.Repository.VocabularyRepository;
import com.chummy_backend.serverside.Service.UsersService;
import com.chummy_backend.serverside.Service.libraryService;

@RestController
@RequestMapping("/api/libraries")
public class LibraryController {
    @Autowired
    private libraryService libraryService;
    @Autowired
    private UsersService usersService;
    @Autowired
    private VocabularyRepository vocabularyRepository;

    @GetMapping
    public ResponseEntity<List<LibraryResponse>> getAllLibraries() {
        try {
            List<LibraryResponse> result = libraryService.findAll().stream().map(this::toResponse)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            throw new BadRequestException("Failed to get libraries");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<LibraryResponse> getLibrary(@PathVariable Long id) {
        try {
            library lib = libraryService.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Library not found"));
            return ResponseEntity.ok(toResponse(lib));
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new BadRequestException("Failed to get library");
        }
    }

    @GetMapping("/owner/{id}")
    public ResponseEntity<List<LibraryResponse>> getLibraryByOwner(@PathVariable Long id) {
        try {
            Users owner = usersService.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("User not found"));

            List<library> libraries = libraryService.findByUser(owner);
            List<LibraryResponse> response = libraries.stream()
                    .map(this::toResponse)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(response);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new BadRequestException("Failed to get library by owner");
        }
    }

    @PostMapping
    public ResponseEntity<LibraryResponse> createLibrary(@RequestBody LibraryRequest request) {
        try {
            library lib = new library();
            lib.setLib_name(request.getLibName());
            Users owner = usersService.findById(request.getOwnerId())
                    .orElseThrow(() -> new ResourceNotFoundException("User not found"));

            lib.setOwnerID(owner);
            List<Vocabulary> vocabularies = vocabularyRepository.findAllById(request.getVocabularyIds());
            Map<Long, Vocabulary> vocabMap = vocabularies.stream()
                    .collect(Collectors.toMap(Vocabulary::getId, v -> v));

            List<library_vocabulary> library_vocabularies = new ArrayList<>();
            for (Long vocabId : request.getVocabularyIds()) {
                Vocabulary vocab = vocabMap.get(vocabId);
                if (vocab == null) {
                    throw new ResourceNotFoundException("Vocabulary not found with ID: " + vocabId);
                }

                library_vocabulary newone = new library_vocabulary();
                newone.setLibrary(lib);
                newone.setVocabulary(vocab);

                library_vocabularies.add(newone);
            }
            lib.setLibrary_vocabularies(library_vocabularies);
            LibraryResponse res = toResponse(libraryService.save(lib));
            return ResponseEntity.ok(res);
        } catch (Exception e) {
            throw new BadRequestException("Failed to create library " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteLibrary(@PathVariable Long id) {
        try {
            libraryService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            throw new BadRequestException("Failed to delete library");
        }
    }

    @PostMapping("/{libraryId}/vocabularies/{vocabularyId}")
    public ResponseEntity<?> addVocabularyToLibrary(@PathVariable Long libraryId, @PathVariable Long vocabularyId) {
        try {
            libraryService.addVocabularyToLibrary(vocabularyId, libraryId);
            return ResponseEntity.ok("Vocabulary added to library successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    private LibraryResponse toResponse(library lib) {
        LibraryResponse res = new LibraryResponse();
        res.setId(lib.getId());
        res.setLibName(lib.getLib_name());

        // Set ownerId and display name
        if (lib.getOwnerID() != null) {
            res.setOwnerId(lib.getOwnerID().getId());
            res.setOwnerDisplayName(lib.getOwnerID().getDisplayName()); // Assuming this method exists
        }

        // Set vocabulary IDs and words
        if (lib.getLibrary_vocabularies() != null && !lib.getLibrary_vocabularies().isEmpty()) {
            List<Long> vocabularyIds = lib.getLibrary_vocabularies().stream()
                    .map(lv -> lv.getVocabulary().getId())
                    .collect(Collectors.toList());

            List<String> vocabularyWords = lib.getLibrary_vocabularies().stream()
                    .map(lv -> lv.getVocabulary().getWord())
                    .collect(Collectors.toList());

            res.setVocabularyIds(vocabularyIds);
            res.setVocabularyWords(vocabularyWords);
        }

        return res;
    }

}
