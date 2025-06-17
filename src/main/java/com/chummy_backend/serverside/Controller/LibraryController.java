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

import com.chummy_backend.serverside.DTO.request.LibraryRequest;
import com.chummy_backend.serverside.DTO.response.LibraryResponse;
import com.chummy_backend.serverside.Exception.BadRequestException;
import com.chummy_backend.serverside.Exception.ResourceNotFoundException;
import com.chummy_backend.serverside.Model.examination.library;
import com.chummy_backend.serverside.Service.libraryService;

@RestController
@RequestMapping("/api/libraries")
public class LibraryController {
    @Autowired
    private libraryService libraryService;

    @GetMapping
    public ResponseEntity<List<LibraryResponse>> getAllLibraries() {
        try {
            List<LibraryResponse> result = libraryService.findAll().stream().map(this::toResponse).collect(Collectors.toList());
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            throw new BadRequestException("Failed to get libraries");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<LibraryResponse> getLibrary(@PathVariable Long id) {
        try {
            library lib = libraryService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Library not found"));
            return ResponseEntity.ok(toResponse(lib));
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new BadRequestException("Failed to get library");
        }
    }

    @PostMapping
    public ResponseEntity<LibraryResponse> createLibrary(@RequestBody LibraryRequest request) {
        try {
            library lib = new library();
            lib.setLib_name(request.getLibName());
            // Set owner and vocabularies if needed
            LibraryResponse res = toResponse(libraryService.save(lib));
            return ResponseEntity.ok(res);
        } catch (Exception e) {
            throw new BadRequestException("Failed to create library");
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

    private LibraryResponse toResponse(library lib) {
        LibraryResponse res = new LibraryResponse();
        res.setId(lib.getId());
        res.setLibName(lib.getLib_name());
        // Set ownerId, ownerDisplayName, vocabularyIds, vocabularyWords if needed
        return res;
    }
}
