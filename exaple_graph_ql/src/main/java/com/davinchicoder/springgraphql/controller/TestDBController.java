package com.davinchicoder.springgraphql.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.davinchicoder.springgraphql.service.FirebaseService;

@RestController
public class TestDBController {

    private final FirebaseService firebaseService;

    public TestDBController(FirebaseService firebaseService) {
        this.firebaseService = firebaseService;
    }

    @GetMapping("/firebase-test")
    public Map<String, Object> testFirebase() {
        return firebaseService.guardarDato(Map.of(
                "message", "Conexion con Firebase correcta",
                "source", "spring-graphql"));
    }

    @PostMapping("/firebase-test")
    public Map<String, Object> guardarDato(@RequestBody Map<String, Object> valores) {
        return firebaseService.guardarDato(valores);
    }

    @GetMapping("/firebase-test/{id}")
    public Map<String, Object> obtenerDato(@PathVariable String id) {
        return firebaseService.obtenerDato(id);
    }
}

