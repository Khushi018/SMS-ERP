package com.aristiec.schoolmanagementsystem.profile;

import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Profile("prod")
public class ProHelloController {

    @GetMapping("/")
    public String sayHello() {
        return "Hello from PROD environment! +5 github workflows";
    }
}

