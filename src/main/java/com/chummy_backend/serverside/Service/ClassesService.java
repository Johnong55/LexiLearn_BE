package com.chummy_backend.serverside.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chummy_backend.serverside.Model.general.Classes;
import com.chummy_backend.serverside.Repository.ClassesRepository;

@Service
public class ClassesService {
    @Autowired
    private ClassesRepository repository;

    public List<Classes> findAll() {
        return repository.findAll();
    }

    public Optional<Classes> findById(Long id) {
        return repository.findById(id);
    }

    public Classes save(Classes entity) {
        return repository.save(entity);
    }
    public Classes update(Long id, Classes entity) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("Class with id " + id + " does not exist");
        }
        entity.setId(id);
        return repository.save(entity);
    }
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
