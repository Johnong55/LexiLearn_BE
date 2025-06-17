package com.chummy_backend.serverside.DTO.response;

import lombok.Data;

@Data
public class ClassPartipationResponse {
    private Long id;
    private Long userId;
    private String userDisplayName;
    private Long classId;
    private String className;
}
