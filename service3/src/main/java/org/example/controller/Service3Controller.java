package org.example.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/service3")
public class Service3Controller {
    private static final Logger logger = LoggerFactory.getLogger(Service3Controller.class);

    @PostMapping("/concat")
    public ResponseEntity<String> concatenate(@Valid @RequestBody Map<String, String> json) {
        String traceId = UUID.randomUUID().toString();
        logger.info("TraceID: {} - Received JSON: {}", traceId, json);

        String name = json.get("Name");
        String surname = json.get("Surname");

        String concatenated = name + " " + surname;
        logger.info("TraceID: {} - Concatenated response: {}", traceId, concatenated);

        return ResponseEntity.ok(concatenated);
    }
}
