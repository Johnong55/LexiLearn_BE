package com.chummy_backend.serverside.DTO.request;

import lombok.Data;

@Data
public class LibraryVocabularyRequest {
    private Long vocabularyId;
    private Long libraryId;
}
