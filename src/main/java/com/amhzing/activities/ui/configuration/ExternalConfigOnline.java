package com.amhzing.activities.ui.configuration;

import com.amhzing.activities.ui.annotation.Online;
import com.amhzing.activities.ui.configuration.properties.ExternalConfigProperties;
import com.amhzing.activities.ui.participant.CircuitBreakingParticipantService;
import com.amhzing.activities.ui.participant.mapping.Participants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Online
@Configuration
@EnableConfigurationProperties(ExternalConfigProperties.class)
public class ExternalConfigOnline {

    @Autowired
    private ExternalConfigProperties externalConfigProperties;

    @LoadBalanced
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public ParticipantService<Failure, Participants> participantService() {
        return new CircuitBreakingParticipantService(restTemplate(), externalConfigProperties.getUrl());
    }
}
