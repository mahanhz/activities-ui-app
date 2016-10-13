package com.amhzing.activities.ui.configuration;

import com.amhzing.activities.ui.annotation.Offline;
import com.amhzing.activities.ui.infra.InMemParticipantService;
import com.amhzing.activities.ui.application.participant.DefaultParticipantService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Offline
@Configuration
public class ExternalConfigOffline {

    @Bean
    public DefaultParticipantService participantService() {
        return new InMemParticipantService();
    }
}
