package com.chummy_backend.serverside.DTO.response;

import lombok.Data;

@Data
public class LibraryVocabularyResponse {
    private Long id;
    private Long vocabularyId;
    private String vocabularyWord;
    private Long libraryId;
    private String libraryName;
}
