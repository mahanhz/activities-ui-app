package com.amhzing.activities.ui.infra.participant;

import com.amhzing.activities.ui.domain.participant.model.Participant;
import org.junit.Test;

import java.util.UUID;

import static com.amhzing.activities.ui.helper.ExternalParticipantHelper.*;
import static org.assertj.core.api.Assertions.assertThat;

public class ParticipantFactoryTest {

    private UUID uuid = UUID.randomUUID();

    @Test
    public void should_create_participant() {
        final Participant participant = ParticipantFactory.createParticipant(participantInfo());

        assertName(participant);
        assertAddress(participant);
        assertThat(participant.getContactNumber().getValue()).isEqualTo(contactNumber().getValue());
        assertThat(participant.getEmail().getValue()).isEqualTo(email().getValue());
    }

    private void assertName(final Participant participant) {
        assertThat(participant.getName().getFirstName()).isEqualTo(name().getFirstName());
        assertThat(participant.getName().getMiddleName()).isEqualTo(name().getMiddleName());
        assertThat(participant.getName().getLastName()).isEqualTo(name().getLastName());
        assertThat(participant.getName().getSuffix()).isEqualTo(name().getSuffix());
    }

    private void assertAddress(final Participant participant) {
        assertThat(participant.getAddress().getAddressLine1()).isEqualTo(address().getAddressLine1());
        assertThat(participant.getAddress().getAddressLine2()).isEqualTo(address().getAddressLine2());
        assertThat(participant.getAddress().getCity()).isEqualTo(address().getCity());
        assertThat(participant.getAddress().getCountry().getCode()).isEqualTo(address().getCountry().getCode());
        assertThat(participant.getAddress().getCountry().getName()).isEqualTo(address().getCountry().getName());
        assertThat(participant.getAddress().getPostalCode()).isEqualTo(address().getPostalCode());
    }
}