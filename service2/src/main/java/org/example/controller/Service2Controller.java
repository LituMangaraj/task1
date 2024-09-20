package org.example.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
@RestController
@RequestMapping("/service2")
public class Service2Controller {
    private static final Logger logger = LoggerFactory.getLogger(Service2Controller.class);

    @GetMapping("/greet")
    public ResponseEntity<String> greet() {
        String traceId = UUID.randomUUID().toString();
        logger.info("TraceID: {} - Returning greeting", traceId);
        return ResponseEntity.ok("Hello");
    }
}
