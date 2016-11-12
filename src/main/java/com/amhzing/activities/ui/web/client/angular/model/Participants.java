package com.amhzing.activities.ui.web.client.angular.model;

import com.amhzing.activities.ui.web.client.model.ParticipantModel;

import java.util.List;
import java.util.Objects;

import static java.util.Collections.emptyList;
import static org.apache.commons.lang3.Validate.noNullElements;

public class Participants {

    private final List<ParticipantModel> participants;
    private final String errorMessage;

    private Participants(final List<ParticipantModel> participants, final String errorMessage) {
        this.participants = noNullElements(participants);
        this.errorMessage = errorMessage;
    }

    public static Participants create(final List<ParticipantModel> participants, final String errorMessage) {
        return new Participants(participants, errorMessage);
    }

    public static Participants createErrorFree(final List<ParticipantModel> participants) {
        return new Participants(participants, "");
    }

    public static Participants createWithError(final String errorMessage) {
        return new Participants(emptyList(), errorMessage);
    }

    public List<ParticipantModel> getParticipants() {
        return participants;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Participants that = (Participants) o;
        return Objects.equals(participants, that.participants) &&
                Objects.equals(errorMessage, that.errorMessage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(participants, errorMessage);
    }

    @Override
    public String toString() {
        return "Participants{" +
                "participants=" + participants +
                ", errorMessage='" + errorMessage + '\'' +
                '}';
    }
}
