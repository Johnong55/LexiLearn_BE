package com.chummy_backend.serverside.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chummy_backend.serverside.Model.general.Class_Partipation;
import com.chummy_backend.serverside.Repository.Class_PartipationRepository;

@Service
public class Class_PartipationService {
    @Autowired
    private Class_PartipationRepository repository;

    public List<Class_Partipation> findAll() {
        return repository.findAll();
    }

    public Optional<Class_Partipation> findById(Long id) {
        return repository.findById(id);
    }

    public Class_Partipation save(Class_Partipation entity) {
        return repository.save(entity);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
