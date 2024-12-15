package com.codecobain.mqtt_spring.config;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

import org.springframework.beans.factory.annotation.Value;

// https://sonjuhy.tistory.com/45
// https://velog.io/@jinony/Spring-Boot-MQTT%EB%A5%BC-%EC%9D%B4%EC%9A%A9%ED%95%9C-%EC%99%B8%EB%B6%80-API%EB%A1%9C%EC%9D%98-%EB%8D%B0%EC%9D%B4%ED%84%B0-%EC%86%A1%EC%88%98%EC%8B%A0
// 스프링이 publish&callback감지, 단말기가 subscribe, 라즈베리파이 mosquitto가 broker
// 토픽 이름으로 단말기 구별..?
// 아래의 명령어로 브로커 먼저 실행
// /opt/homebrew/opt/mosquitto/sbin/mosquitto -c /opt/homebrew/etc/mosquitto/mosquitto.conf
// 아래 링크 참고해서 .lck파일 생성 문제 해결 찾아보기
// https://stackoverflow.com/questions/66479612/eclipse-paho-mqtt-generates-a-lot-of-folders-containing-a-lck-file
// 아래 맄크 참고해서 callback 구현
// https://velog.io/@yeoonnii/mqtt-client

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

    // subscribe로 받아온 값을 전역으로 관리하기 위한 bean객체 등록
    @Bean
    public MessageContainer messageContainer() {
        return new MessageContainer();
    }
}