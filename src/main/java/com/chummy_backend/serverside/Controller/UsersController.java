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

import com.chummy_backend.serverside.DTO.request.UsersRequest;
import com.chummy_backend.serverside.DTO.response.UsersResponse;
import com.chummy_backend.serverside.Exception.BadRequestException;
import com.chummy_backend.serverside.Exception.ResourceNotFoundException;
import com.chummy_backend.serverside.Model.general.Users;
import com.chummy_backend.serverside.Service.UsersService;

@RestController
@RequestMapping("/api/users")
public class UsersController {
    @Autowired
    private UsersService usersService;

    @GetMapping
    public ResponseEntity<List<UsersResponse>> getAllUsers() {
        try {
            List<UsersResponse> result = usersService.findAll().stream().map(this::toResponse).collect(Collectors.toList());
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            throw new BadRequestException("Failed to get users");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsersResponse> getUser(@PathVariable Long id) {
        try {
            Users u = usersService.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
            return ResponseEntity.ok(toResponse(u));
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new BadRequestException("Failed to get user");
        }
    }

    @PostMapping
    public ResponseEntity<UsersResponse> createUser(@RequestBody UsersRequest request) {
        try {
            Users u = new Users();
            u.setDisplayName(request.getDisplayName());
            u.setEmail(request.getEmail());
            u.setPassword(request.getPassword());
            UsersResponse res = toResponse(usersService.save(u));
            return ResponseEntity.ok(res);
        } catch (Exception e) {
            throw new BadRequestException("Failed to create user");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        try {
            usersService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            throw new BadRequestException("Failed to delete user");
        }
    }

    private UsersResponse toResponse(Users u) {
        UsersResponse res = new UsersResponse();
        res.setId(u.getId());
        res.setDisplayName(u.getDisplayName());
        res.setEmail(u.getEmail());
        res.setCreateAt(u.getCreateAt());
        // Set classListIds if needed
        return res;
    }
}
