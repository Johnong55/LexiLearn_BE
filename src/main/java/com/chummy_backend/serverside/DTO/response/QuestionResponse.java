package com.chummy_backend.serverside.DTO.response;

import java.util.List;

import lombok.Data;

@Data
public class QuestionResponse {
    private Long id;
    private String content;
    private List<String> choices;
    private String correctAnswer;
    private String questionType;
    private String explanation; // Thêm trường explanation
    private VocabularyResponse vocabulary;
}