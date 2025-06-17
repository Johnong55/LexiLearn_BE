package com.chummy_backend.serverside.DTO.response;

import java.util.List;

import lombok.Data;

@Data
public class ClassesResponse {
    private Long id;
    private String className;
    private List<Long> partipationIds;
    private List<String> partipationUserNames;
}
