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

import com.chummy_backend.serverside.DTO.request.ClassesRequest;
import com.chummy_backend.serverside.DTO.response.ClassesResponse;
import com.chummy_backend.serverside.Exception.BadRequestException;
import com.chummy_backend.serverside.Exception.ResourceNotFoundException;
import com.chummy_backend.serverside.Model.general.Classes;
import com.chummy_backend.serverside.Service.ClassesService;

@RestController
@RequestMapping("/api/classes")
public class ClassesController {
    @Autowired
    private ClassesService classesService;

    @GetMapping
    public ResponseEntity<List<ClassesResponse>> getAllClasses() {
        try {
            List<ClassesResponse> result = classesService.findAll().stream().map(this::toResponse).collect(Collectors.toList());
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            throw new BadRequestException("Failed to get classes");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClassesResponse> getClass(@PathVariable Long id) {
        try {
            Classes c = classesService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Class not found"));
            return ResponseEntity.ok(toResponse(c));
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new BadRequestException("Failed to get class");
        }
    }

    @PostMapping
    public ResponseEntity<ClassesResponse> createClass(@RequestBody ClassesRequest request) {
        try {
            Classes c = new Classes();
            c.setClassName(request.getClassName());
            // Set participations if needed
            ClassesResponse res = toResponse(classesService.save(c));
            return ResponseEntity.ok(res);
        } catch (Exception e) {
            throw new BadRequestException("Failed to create class");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteClass(@PathVariable Long id) {
        try {
            classesService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            throw new BadRequestException("Failed to delete class");
        }
    }

    private ClassesResponse toResponse(Classes c) {
        ClassesResponse res = new ClassesResponse();
        res.setId(c.getId());
        res.setClassName(c.getClassName());
        // Set partipationIds, partipationUserNames if needed
        return res;
    }
}
