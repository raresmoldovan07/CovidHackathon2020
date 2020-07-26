package com.covid.hackathon;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.apache.commons.codec.binary.Base64;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableAsync
public class HackathonConfiguration {

    @Bean
    public Base64 base64(){
        return new Base64();
    }

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
