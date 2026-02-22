package alistle.com.userservice.application.dto.request;

import lombok.Data;

@Data
public class CreateUserRequest {
    private String email;
    private String password;
}
