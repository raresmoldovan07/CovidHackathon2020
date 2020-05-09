package com.covid.hackathon.business.util;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.apache.commons.codec.binary.Base64;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppComponents {

    @Bean
    public Base64 base64(){
        return new Base64();
    }

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
