package com.chummy_backend.serverside.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chummy_backend.serverside.Model.examination.library;
import com.chummy_backend.serverside.Model.examination.library_vocabulary;
import com.chummy_backend.serverside.Repository.library_vocabularyRepository;

@Service
public class library_vocabularyService {
    @Autowired
    private library_vocabularyRepository repository;

    public List<library_vocabulary> findAll() {
        return repository.findAll();
    }

    public Optional<library_vocabulary> findById(Long id) {
        return repository.findById(id);
    }

    public library_vocabulary save(library_vocabulary entity) {
        return repository.save(entity);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
    public List<library_vocabulary> findByLibraries(library Library)
    {

        return repository.findByLibrary(Library);

    }
}
