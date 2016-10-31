package com.amhzing.activities.ui.external.participant;

import com.amhzing.activities.ui.external.participant.request.SearchSpecification;
import com.amhzing.activities.ui.external.participant.response.ParticipantResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.client.RestTemplate;

@RunWith(MockitoJUnitRunner.class)
public class SearchExternalParticipantServiceTest {

    @Mock
    private RestTemplate restTemplate;

    private SearchExternalParticipantService externalParticipantService;

    @Before
    public void before() {
        externalParticipantService = new SearchExternalParticipantService(restTemplate, "/url");
    }

    @Test
    public void should_get_participants() {
        externalParticipantService.searchParticipants(searchSpecification());

        Mockito.verify(restTemplate, Mockito.times(1)).getForObject("/url/query/SE/a/b/c/d", ParticipantResponse.class);
    }

    private SearchSpecification searchSpecification() {
        return SearchSpecification.create("SE", "a", "b", "c", "d");
    }
}