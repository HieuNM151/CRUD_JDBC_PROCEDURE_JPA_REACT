package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class HelloController {
    @GetMapping("/")
    public Map<String, Object> hello(){
        return Map.of("message", "Hello world");
    }
}
