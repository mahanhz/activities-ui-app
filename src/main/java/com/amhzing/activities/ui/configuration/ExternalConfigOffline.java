package com.amhzing.activities.ui.configuration;

import com.amhzing.activities.ui.annotation.Offline;
import com.amhzing.activities.ui.participant.InMemParticipantService;
import com.amhzing.activities.ui.participant.mapping.Participants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Offline
@Configuration
public class ExternalConfigOffline {

    @Bean
    public ParticipantService<Failure, Participants> participantService() {
        return new InMemParticipantService();
    }
}
