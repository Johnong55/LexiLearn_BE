package com.chummy_backend.serverside.DTO.request;

import lombok.Data;

@Data
public class QuestionRequest {
    private String content;
    private Long vocabularyId;
}
