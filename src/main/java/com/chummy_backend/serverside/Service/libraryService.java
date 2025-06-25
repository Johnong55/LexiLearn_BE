package com.chummy_backend.serverside.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chummy_backend.serverside.Model.examination.Vocabulary;
import com.chummy_backend.serverside.Model.examination.library;
import com.chummy_backend.serverside.Model.examination.library_vocabulary;
import com.chummy_backend.serverside.Model.general.Users;
import com.chummy_backend.serverside.Repository.VocabularyRepository;
import com.chummy_backend.serverside.Repository.libraryRepository;
import com.chummy_backend.serverside.Repository.library_vocabularyRepository;

@Service
public class libraryService {
    @Autowired
    private libraryRepository repository;
    @Autowired
    private VocabularyRepository vocabularyRepository;

    @Autowired
    private library_vocabularyRepository libraryVocabularyRepository;

    public List<library> findAll() {
        return repository.findAll();
    }

    public List<library> findByUser(Users ownerID) {
        return repository.findByOwnerID(ownerID);
    }

    public Optional<library> findById(Long id) {
        return repository.findById(id);
    }

    public library save(library entity) {
        return repository.save(entity);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public void addVocabularyToLibrary(Long vocabularyId, Long libraryId) {
        library lib = repository.findById(libraryId)
                .orElseThrow(() -> new RuntimeException("Library not found"));

        Vocabulary vocab = vocabularyRepository.findById(vocabularyId)
                .orElseThrow(() -> new RuntimeException("Vocabulary not found"));

        boolean exists = libraryVocabularyRepository.existsByLibraryAndVocabulary(lib, vocab);
        if (exists) {
            throw new RuntimeException("This vocabulary already exists in the library");
        }

        library_vocabulary lv = library_vocabulary.builder()
                .library(lib)
                .vocabulary(vocab)
                .build();

        libraryVocabularyRepository.save(lv);
    }

}
