package com.codecobain.mqtt_spring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MqttMessagePublishRequestDto {
    private String topic;
    private String message;
}
