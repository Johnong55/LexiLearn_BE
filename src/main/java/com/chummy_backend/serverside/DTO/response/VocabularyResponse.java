package com.chummy_backend.serverside.DTO.response;

import java.util.List;

import lombok.Data;

@Data
public class VocabularyResponse {
    private Long id;
    private String word;
    private String meaning;
    private List<Long> questionIds;
}
