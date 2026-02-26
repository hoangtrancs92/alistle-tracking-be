package alistle.com.identifyservice.controller.http;

import alistle.com.identifyservice.application.dto.request.CreateUserRequest;
import alistle.com.identifyservice.application.dto.request.IntrospectRequest;
import alistle.com.identifyservice.application.dto.request.LoginRequest;
import alistle.com.identifyservice.application.dto.request.RefreshRequest;
import alistle.com.identifyservice.application.dto.response.*;
import alistle.com.identifyservice.application.service.UserAppService;
import com.nimbusds.jose.JOSEException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.text.ParseException;


@RestController
@RequestMapping("/api/v1/users")
@Slf4j
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {
    UserAppService userAppService;

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody CreateUserRequest request) {
        try {
            UserResponse response = userAppService.createUser(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            log.error("Error creating user: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/by-email")
    public ApiResponse<UserResponse> getUserByEmail(@RequestParam String email) {
        try {
            return ApiResponse.<UserResponse>builder().
                    result(userAppService.getUserByEmail(email)).build();
        } catch (IllegalArgumentException e) {
            log.error("Error fetching user: {}", e.getMessage());
            return null; // In a real application, you might want to return a proper error response here
        }
    }

    @PostMapping("/login")
    public ApiResponse<AuthenticationResponse> login(@RequestBody LoginRequest request) {
        return ApiResponse.<AuthenticationResponse>builder().result(userAppService.login(request)).build();
    }

    @PostMapping("/introspect")
    public ApiResponse<IntrospectResponse> introspect(@RequestBody IntrospectRequest request) {
        return ApiResponse.<IntrospectResponse>builder().result(userAppService.introspect(request)).build();
    }

    @PostMapping("/refresh")
    public  ApiResponse<RefreshResponse> refresh(@RequestBody RefreshRequest refreshRequest) throws ParseException, JOSEException {
        return ApiResponse.<RefreshResponse>builder().result(userAppService.refreshToken(refreshRequest)).build();
    }
}
