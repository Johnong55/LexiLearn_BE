package com.chummy_backend.serverside.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chummy_backend.serverside.Model.examination.library;
import com.chummy_backend.serverside.Repository.libraryRepository;

@Service
public class libraryService {
    @Autowired
    private libraryRepository repository;

    public List<library> findAll() {
        return repository.findAll();
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
}
