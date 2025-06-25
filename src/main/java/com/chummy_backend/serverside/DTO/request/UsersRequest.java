package com.chummy_backend.serverside.DTO.request;

import lombok.Data;

@Data
public class UsersRequest {
    private String displayName;
    private String email;
    private String password;
    
}
