package com.codecobain.mqtt_spring.service;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.stereotype.Service;

import com.codecobain.mqtt_spring.dto.MqttMessagePublishRequestDto;

import java.nio.charset.StandardCharsets;

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
            // 메시지 publish
            client.publish(dto.getTopic(), message);

            // 돌아오는 응답을 얻기 위한 subscribe
            client.subscribe(dto.getTopic(), (topic, msg) -> {
                String result = new String(msg.getPayload(), StandardCharsets.UTF_8);
                System.out.println(result);
            });
        } catch (MqttException e) {
            e.printStackTrace();
            System.out.println("fail to publish");
        }
    }

    public void closeSubscribe(String topic) {
        try {
            client.unsubscribe(topic);
        } catch (Exception e) {
            // throw new MqttException();
            // TODO: handle exception
        }
        
    }

    public void getMessage() {
        try {
            client.subscribe("test", (topic, msg) -> {
                // if (msg.getPayload().length != 0) {
                    String result = new String(msg.getPayload(), StandardCharsets.UTF_8);
                    System.out.println(result);
                // }
            });
            // client.unsubscribe("test");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("fail to load message");
        }
    }
}
