package com.chummy_backend.serverside.DTO.response;

import lombok.Data;

@Data
public class QuestionResponse {
    private Long id;
    private String content;
    private Long vocabularyId;
    private String vocabularyWord;
}
