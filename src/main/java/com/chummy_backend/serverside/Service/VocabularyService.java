package com.chummy_backend.serverside.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chummy_backend.serverside.Model.examination.Vocabulary;
import com.chummy_backend.serverside.Model.examination.library;
import com.chummy_backend.serverside.Model.examination.library_vocabulary;
import com.chummy_backend.serverside.Repository.VocabularyRepository;

@Service
public class VocabularyService {
    @Autowired
    private VocabularyRepository repository;
    @Autowired
    private library_vocabularyService service;
    public List<Vocabulary> findAll() {
        return repository.findAll();
    }

    public Optional<Vocabulary> findById(Long id) {
        return repository.findById(id);
    }

    public Vocabulary save(Vocabulary entity) {
        return repository.save(entity);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
    public List<Vocabulary> findByLibrary(library Library)
    {   
        List<Vocabulary> results = new ArrayList<>();
        List<library_vocabulary> vocabularies = service.findByLibraries(Library);
        vocabularies.forEach(vocab -> results.add(vocab.getVocabulary()));
        return results;
    }
    
   
}
