package org.example.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.UUID;


@RestController
@RequestMapping("/service1")
public class Service1Controller {
    @Autowired
    private RestTemplate restTemplate;

    private static final Logger logger = (Logger) LoggerFactory.getLogger(Service1Controller.class);

    @GetMapping("/status")
    public ResponseEntity<String> getStatus() {
        logger.info("TraceID: {} - Checking service status", UUID.randomUUID());
        return ResponseEntity.ok("Up");
    }

    @PostMapping("/process")
    public ResponseEntity<String> processJson(@RequestBody Map<String, String> json) {
        String traceId = UUID.randomUUID().toString();
        logger.info("TraceID: {} - Starting orchestration", traceId);

        try {
            // Call Service 2
            String service2Response = restTemplate.getForObject("http://localhost:8081/service2/greet", String.class);
            logger.info("TraceID: {} - Service 2 response: {}", traceId, service2Response);

            // Call Service 3
            String service3Response = restTemplate.postForObject("http://localhost:8082/service3/concat", json, String.class);
            logger.info("TraceID: {} - Service 3 response: {}", traceId, service3Response);

            String finalResponse = service2Response + " " + service3Response;
            logger.info("TraceID: {} - Final response: {}", traceId, finalResponse);

            return ResponseEntity.ok(finalResponse);

        } catch (HttpClientErrorException e) {
            logger.error("TraceID: {} - Error occurred during REST call: {}", traceId, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing the request.");
        } catch (Exception e) {
            logger.error("TraceID: {} - Unknown error: {}", traceId, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unknown error occurred.");
        }
    }
}
