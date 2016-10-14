package com.amhzing.activities.ui.external.participant;

import com.amhzing.activities.ui.external.participant.request.SearchSpecification;
import com.amhzing.activities.ui.external.participant.response.ParticipantResponse;
import org.springframework.web.client.RestTemplate;

import static org.apache.commons.lang3.Validate.notNull;

public class SearchExternalParticipantService implements ExternalParticipantService {

    private RestTemplate restTemplate;
    private String participantUrl;

    public SearchExternalParticipantService(final RestTemplate restTemplate, final String participantUrl) {
        this.restTemplate = notNull(restTemplate);
        this.participantUrl = notNull(participantUrl);
    }

    @Override
    public ParticipantResponse searchParticipants(final SearchSpecification searchSpecification) {
        notNull(searchSpecification);

        final String url = participantUrl + "/query/" + uri(searchSpecification);
        return restTemplate.getForObject(url, ParticipantResponse.class);
    }

    private String uri(final SearchSpecification searchSpecification) {
        return searchSpecification.getCountry()
                + "/" +
                searchSpecification.getCity()
                + "/" +
                searchSpecification.getAddressLine1()
                + "/" +
                searchSpecification.getLastName()
                + "/" +
                searchSpecification.getParticipantId();

    }
}
