
package com.chummy_backend.serverside.DTO.request;

import lombok.Data;

@Data
public class QuestionGenerationRequest {
    private String word;
    private String meaning;
    private String questionType; // "MULTIPLE_CHOICE" hoặc "FILL_IN_BLANK"
    private int numberOfChoices = 4; // Mặc định 4 lựa chọn
    private String difficulty = "MEDIUM"; // EASY, MEDIUM, HARD
    private String context; // Ngữ cảnh để tạo câu hỏi phù hợp
}