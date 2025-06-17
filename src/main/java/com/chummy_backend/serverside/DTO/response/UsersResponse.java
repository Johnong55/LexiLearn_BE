package com.chummy_backend.serverside.DTO.response;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

@Data
public class UsersResponse {
    private Long id;
    private String displayName;
    private String email;
    private LocalDateTime createAt;
    private List<Long> classListIds;
}
