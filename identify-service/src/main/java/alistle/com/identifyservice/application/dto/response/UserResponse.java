package alistle.com.identifyservice.application.dto.response;

import lombok.Data;

@Data
public class UserResponse {
    private Long id;
    private String email;

    public UserResponse(Long id, String email) {
        this.id = id;
        this.email = email;
    }
}
