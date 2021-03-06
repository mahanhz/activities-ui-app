package com.amhzing.activities.ui.configuration;

import com.amhzing.activities.ui.annotation.Online;
import com.amhzing.activities.ui.configuration.properties.ExternalConfigProperties;
import com.amhzing.activities.ui.external.participant.ExternalParticipantService;
import com.amhzing.activities.ui.external.participant.SearchExternalParticipantService;
import com.amhzing.activities.ui.infra.participant.CircuitBreakingParticipantRepository;
import com.amhzing.activities.ui.infra.participant.DefaultParticipantRepository;
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
    public ExternalParticipantService externalParticipantService() {
        return new SearchExternalParticipantService(restTemplate(), externalConfigProperties.getUrl());
    }

    @Bean
    public DefaultParticipantRepository participantRepository() {
        return new CircuitBreakingParticipantRepository(externalParticipantService());
    }
}
