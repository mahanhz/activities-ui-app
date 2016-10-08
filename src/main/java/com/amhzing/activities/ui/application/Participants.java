package com.amhzing.activities.ui.application;

import com.amhzing.activities.ui.domain.participant.Participant;
import com.google.common.collect.ImmutableList;

import java.util.List;
import java.util.Objects;

import static org.apache.commons.lang3.Validate.noNullElements;

public class Participants {

    private final List<Participant> participants;

    private Participants(final List<Participant> participants) {
        this.participants = noNullElements(participants);
    }

    public static Participants create(final List<Participant> participants) {
        noNullElements(participants);

        return new Participants(participants);
    }

    public List<Participant> getParticipants() {
        return ImmutableList.copyOf(participants);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Participants that = (Participants) o;
        return Objects.equals(participants, that.participants);
    }

    @Override
    public int hashCode() {
        return Objects.hash(participants);
    }

    @Override
    public String toString() {
        return "Participants{" +
                "participants=" + participants +
                '}';
    }
}
