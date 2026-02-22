package alistle.com.userservice.application.dto.response;

import lombok.Data;

import java.util.UUID;

@Data
public class UserResponse {
    private UUID id;
    private String email;

    public UserResponse(UUID id, String email) {
        this.id = id;
        this.email = email;
    }
}
