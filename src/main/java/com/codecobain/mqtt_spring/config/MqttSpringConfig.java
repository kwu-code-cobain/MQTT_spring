package com.codecobain.mqtt_spring.config;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;


@EnableAsync
@Configuration
public class MqttSpringConfig {
    // MQTT 클라이언트를 빈으로 등록하고 브로커와 연결
    @Bean
    public MqttClient mqttClient(@Value("${mqtt.clientId}") String clientId, @Value("${mqtt.hostname}") String hostname,
            @Value("${mqtt.port}") int port)
            throws MqttException {
        MqttClient mqttClient = new MqttClient("tcp://" + hostname + ":" + port, clientId);

        try {
            mqttClient.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return mqttClient;
    }
}
