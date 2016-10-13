package com.amhzing.activities.ui.external.participant;


import com.amhzing.activities.ui.external.participant.request.SearchSpecification;
import com.amhzing.activities.ui.external.participant.response.ParticipantResponse;

public interface ExternalParticipantService {

    ParticipantResponse searchParticipants(SearchSpecification searchSpecification);
}
