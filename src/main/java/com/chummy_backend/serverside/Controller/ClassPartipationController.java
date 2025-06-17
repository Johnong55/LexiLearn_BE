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

import com.chummy_backend.serverside.DTO.request.ClassPartipationRequest;
import com.chummy_backend.serverside.DTO.response.ClassPartipationResponse;
import com.chummy_backend.serverside.Exception.BadRequestException;
import com.chummy_backend.serverside.Exception.ResourceNotFoundException;
import com.chummy_backend.serverside.Model.general.Class_Partipation;
import com.chummy_backend.serverside.Service.Class_PartipationService;

@RestController
@RequestMapping("/api/class-partipations")
public class ClassPartipationController {
    @Autowired
    private Class_PartipationService classPartipationService;

    @GetMapping
    public ResponseEntity<List<ClassPartipationResponse>> getAllClassPartipations() {
        try {
            List<ClassPartipationResponse> result = classPartipationService.findAll().stream().map(this::toResponse).collect(Collectors.toList());
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            throw new BadRequestException("Failed to get class partipations");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClassPartipationResponse> getClassPartipation(@PathVariable Long id) {
        try {
            Class_Partipation cp = classPartipationService.findById(id).orElseThrow(() -> new ResourceNotFoundException("ClassPartipation not found"));
            return ResponseEntity.ok(toResponse(cp));
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new BadRequestException("Failed to get class partipation");
        }
    }

    @PostMapping
    public ResponseEntity<ClassPartipationResponse> createClassPartipation(@RequestBody ClassPartipationRequest request) {
        try {
            Class_Partipation cp = new Class_Partipation();
            // Set user and class if needed
            ClassPartipationResponse res = toResponse(classPartipationService.save(cp));
            return ResponseEntity.ok(res);
        } catch (Exception e) {
            throw new BadRequestException("Failed to create class partipation");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteClassPartipation(@PathVariable Long id) {
        try {
            classPartipationService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            throw new BadRequestException("Failed to delete class partipation");
        }
    }

    private ClassPartipationResponse toResponse(Class_Partipation cp) {
        ClassPartipationResponse res = new ClassPartipationResponse();
        res.setId(cp.getId());
        // Set userId, userDisplayName, classId, className if needed
        return res;
    }
}
