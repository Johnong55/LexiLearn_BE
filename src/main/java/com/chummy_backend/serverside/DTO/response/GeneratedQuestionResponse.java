package com.chummy_backend.serverside.DTO.response;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GeneratedQuestionResponse {
    private String content;
    private List<String> choices;
    private String correctAnswer;
    private String explanation;
    private String questionType;

    
}
