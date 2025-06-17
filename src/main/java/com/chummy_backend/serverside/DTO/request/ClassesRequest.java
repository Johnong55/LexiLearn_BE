package com.chummy_backend.serverside.DTO.request;

import java.util.List;

import lombok.Data;

@Data
public class ClassesRequest {
    private String className;
    private List<Long> partipationIds;
    
}
