package com.amhzing.activities.ui.configuration;

import com.amhzing.activities.ui.annotation.Offline;
import com.amhzing.activities.ui.external.participant.InMemParticipantService;
import com.amhzing.activities.ui.infra.DefaultParticipantService;
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
