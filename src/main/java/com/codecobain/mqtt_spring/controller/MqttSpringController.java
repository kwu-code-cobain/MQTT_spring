package com.codecobain.mqtt_spring.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.codecobain.mqtt_spring.dto.MqttMessagePublishRequestDto;
import com.codecobain.mqtt_spring.service.MqttSpringService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequiredArgsConstructor
public class MqttSpringController {
    private final MqttSpringService service;

    @PostMapping("publish")
    public ResponseEntity<?> messaggePublish(@RequestBody MqttMessagePublishRequestDto dto) {
        try {
            service.messagePublish(dto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
        return ResponseEntity.ok("success to publish message");
    }

    @GetMapping("message")
    public ResponseEntity<?> getMessage() {
        try {
            service.getMessage();
            return ResponseEntity.ok("success to get message");
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            // TODO: handle exception
        }
        
    }
    

}
