package com.aristiec.schoolmanagementsystem.profile;

import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    // @GetMapping("/")
    // @ConditionalOnExpression("'${spring.profiles.active}'=='dev'")
    // public String rootTestDev() {
    //     return "Root is working! (DEV only)";
    // }

    // @GetMapping("/")
    // @ConditionalOnExpression("'${spring.profiles.active}'=='prod'")
    // public String rootTestProd() {
    //     return "Root is working! (PROD only)";
    // }
}