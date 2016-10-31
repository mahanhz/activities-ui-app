package com.amhzing.activities.ui.infra.participant;

import com.amhzing.activities.ui.domain.participant.model.Participant;
import com.google.common.collect.ImmutableList;
import org.junit.Test;

import static com.amhzing.activities.ui.helper.DomainParticipantHelper.*;
import static org.assertj.core.api.Assertions.assertThat;

public class ParticipantsTest {

    @Test
    public void should_create_participants() {
        final Participants participants = Participants.create(ImmutableList.of(participant()));

        assertThat(participants.getParticipants()).containsExactly(participant());
    }

    private Participant participant() {
        return Participant.create("pId", name(), address(), contactNumber(), email());
    }
}