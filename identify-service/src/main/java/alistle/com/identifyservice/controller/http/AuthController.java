package alistle.com.identifyservice.controller.http;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/identify")
public class AuthController {
    @GetMapping("/auth")
    public String auth() {
        return "auth success";
    }
}
