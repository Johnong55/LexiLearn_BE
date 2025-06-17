package com.chummy_backend.serverside.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chummy_backend.serverside.Model.examination.Vocabulary;
import com.chummy_backend.serverside.Repository.VocabularyRepository;

@Service
public class VocabularyService {
    @Autowired
    private VocabularyRepository repository;

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
}
