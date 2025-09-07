package com.aristiec.schoolmanagementsystem.profile;

import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.*;

@RestController
@Profile("dev")
public class DevHelloController {

    @GetMapping("/")
    public String sayHello() {
        return "Hello from DEV environment!";
    }
}
