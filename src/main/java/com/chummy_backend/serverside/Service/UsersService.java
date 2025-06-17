package com.chummy_backend.serverside.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chummy_backend.serverside.Model.general.Users;
import com.chummy_backend.serverside.Repository.UsersRepository;

@Service
public class UsersService {
    @Autowired
    private UsersRepository repository;

    public List<Users> findAll() {
        return repository.findAll();
    }

    public Optional<Users> findById(Long id) {
        return repository.findById(id);
    }

    public Users save(Users entity) {
        return repository.save(entity);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
