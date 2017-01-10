package org.edu.reksoft.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableScheduling
public class TeamCityConfiguration {

    @Value("${teamcity.username}")
    private String username;

    @Value("${teamcity.password}")
    private String password;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplateBuilder()
                .basicAuthorization(username, password)
                .build();
    }
}