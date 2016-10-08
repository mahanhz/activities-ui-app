package com.amhzing.activities.ui.configuration;

import com.amhzing.activities.ui.annotation.Offline;
import com.amhzing.activities.ui.external.participant.InMemParticipantService;
import com.amhzing.activities.ui.query.data.participant.Failure;
import com.amhzing.activities.ui.query.data.participant.ParticipantService;
import com.amhzing.activities.ui.query.data.participant.mapping.Participants;
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
