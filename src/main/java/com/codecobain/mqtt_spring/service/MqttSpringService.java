package com.codecobain.mqtt_spring.service;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.stereotype.Service;

import com.codecobain.mqtt_spring.dto.MqttMessagePublishRequestDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MqttSpringService {
    private final MqttClient client;

    public void messagePublish(MqttMessagePublishRequestDto dto) {
        String messageContent = dto.getMessage();

        // publish할 메시지 생성
        MqttMessage message = new MqttMessage();
        message.setPayload(messageContent.getBytes());

        // 클라이언트를 통해 현재 연결 중인 브로커에 메시지를 publish
        try {
            client.publish(dto.getTopic(), message);
        } catch (MqttException e) {
            e.printStackTrace();
            System.out.println("fail to publish");
        }
    }
}
