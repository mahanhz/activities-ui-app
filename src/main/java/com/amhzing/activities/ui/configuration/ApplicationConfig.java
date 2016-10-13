package com.amhzing.activities.ui.configuration;

import com.amhzing.activities.ui.application.participant.ParticipantService;
import com.amhzing.activities.ui.infra.participant.DefaultParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({ExternalConfig.class})
public class ApplicationConfig {

    @Autowired
    DefaultParticipantService defaultParticipantService;

    @Bean
    public ParticipantService appParticipantService() {
        return new ParticipantService(defaultParticipantService);
    }
}
