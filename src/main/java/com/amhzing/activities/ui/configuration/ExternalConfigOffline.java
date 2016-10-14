package com.amhzing.activities.ui.configuration;

import com.amhzing.activities.ui.annotation.Offline;
import com.amhzing.activities.ui.infra.participant.InMemParticipantRepository;
import com.amhzing.activities.ui.infra.participant.DefaultParticipantRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Offline
@Configuration
public class ExternalConfigOffline {

    @Bean
    public DefaultParticipantRepository participantRepository() {
        return new InMemParticipantRepository();
    }
}
