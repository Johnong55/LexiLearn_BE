package com.chummy_backend.serverside.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chummy_backend.serverside.Model.examination.question;
import com.chummy_backend.serverside.Repository.questionRepository;

@Service
public class questionService {
    @Autowired
    private questionRepository repository;

    public List<question> findAll() {
        return repository.findAll();
    }

    public Optional<question> findById(Long id) {
        return repository.findById(id);
    }

    public question save(question entity) {
        return repository.save(entity);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
