package com.chummy_backend.serverside.DTO.request;

import lombok.Data;

@Data
public class ClassPartipationRequest {
    private Long userId;
    private Long classId;
}
