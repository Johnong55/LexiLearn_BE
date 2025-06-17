package com.chummy_backend.serverside.DTO.request;

import java.util.List;

import lombok.Data;

@Data
public class LibraryRequest {
    private String libName;
    private Long ownerId;
    private List<Long> vocabularyIds;
}
