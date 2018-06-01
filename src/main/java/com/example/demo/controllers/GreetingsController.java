package com.example.demo.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingsController {

    @GetMapping("/greeting")
    public String readyForPalGreeting() {
        return "I'm ready for PAL";
    }
}
