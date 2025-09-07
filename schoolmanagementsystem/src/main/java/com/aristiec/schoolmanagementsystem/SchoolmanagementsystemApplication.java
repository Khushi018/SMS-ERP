package com.aristiec.schoolmanagementsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;


@OpenAPIDefinition(
    servers = {
        @Server(url = "https://6e1a53647dbe.ngrok-free.app/")
    }
)
@SpringBootApplication
public class SchoolmanagementsystemApplication {
    public static void main(String[] args) {
        SpringApplication.run(SchoolmanagementsystemApplication.class, args);
    }
}
