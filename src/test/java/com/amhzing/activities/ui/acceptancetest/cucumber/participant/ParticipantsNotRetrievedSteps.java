package com.amhzing.activities.ui.acceptancetest.cucumber.participant;

import com.amhzing.activities.ui.acceptancetest.cucumber.SpringSteps;
import com.amhzing.activities.ui.application.participant.ParticipantService;
import com.amhzing.activities.ui.domain.participant.model.Participant;
import com.amhzing.activities.ui.domain.participant.repository.QueryCriteria;
import cucumber.api.java8.En;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.apache.commons.lang3.Validate.notNull;
import static org.assertj.core.api.Assertions.assertThat;

public class ParticipantsNotRetrievedSteps extends SpringSteps implements En {

    private List<Participant> participants;
    private QueryCriteria queryCriteria;

    @Autowired
    public ParticipantsNotRetrievedSteps(final ParticipantService participantService) {
        notNull(participantService);

        Given("^search by invalid country \"([^\"]*)\"$", (String country) -> {
            queryCriteria = queryCriteria(country);
        });

        When("^attempting to retrieve participants$", () -> {
            participants = participantService.getParticipants(queryCriteria);
        });

        Then("^participants are not retrieved$", () -> {
            assertThat(participants).isEmpty();
        });
    }

    private QueryCriteria queryCriteria(final String country) {
        return QueryCriteria.create(country, "", "", "", "");
    }
}
