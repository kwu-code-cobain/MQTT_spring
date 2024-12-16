package com.codecobain.mqtt_spring.service;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.stereotype.Service;

import com.codecobain.mqtt_spring.config.MessageContainer;
import com.codecobain.mqtt_spring.dto.MqttMessagePublishRequestDto;
import com.codecobain.mqtt_spring.dto.MqttSubscribeDto;

import java.nio.charset.StandardCharsets;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MqttSpringService {
    private final MqttClient client;
    private final MessageContainer messageContainer;

    // 기기 조작을 위한 publish
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

    // subscribe 종료
    public void closeSubscribe(String topic) {
        try {
            client.unsubscribe("zigbee2mqtt/" + topic);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    // 특정 topic에 대한 subscribe 시작
    public void subscribeTopic(String topicStr) {
        // System.out.println(str);
        // 특정 topic을 subscribe, 실시간으로 갱신되는 값을 bean객체에 계속 갱신
        try {
            client.subscribe("zigbee2mqtt/" + topicStr, (topic, msg) -> {
                String result = new String(msg.getPayload(), StandardCharsets.UTF_8);
                // bean객체에 새로운 값을 갱신
                messageContainer.setMessage(result);
                System.out.println(result);
            });
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("fail to load message");
        }
    }
    
    // bean에 저장된 값을 불러옴
    public MqttSubscribeDto getMessage() {
        return new MqttSubscribeDto(messageContainer.getMessage());
    }
}
