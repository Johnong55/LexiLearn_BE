package com.chummy_backend.serverside.DTO.response;

import java.util.List;

import lombok.Data;

@Data
public class LibraryResponse {
    private Long id;
    private String libName;
    private Long ownerId;
    private String ownerDisplayName;
    private List<Long> vocabularyIds;
    private List<String> vocabularyWords;
}
