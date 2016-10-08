package com.amhzing.activities.ui.external.participant.response;

import com.amhzing.activities.ui.external.participant.domain.ParticipantInfo;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableList;

import java.util.List;
import java.util.Objects;

@JsonInclude
public class ParticipantResponse {

    private final List<ParticipantInfo> participants;
    private final List<ErrorResponse> errors;

    private ParticipantResponse(final List<ParticipantInfo> participants, final List<ErrorResponse> errors) {
        this.participants = participants;
        this.errors = errors;
    }

    @JsonCreator
    public static ParticipantResponse create(@JsonProperty("participants") final List<ParticipantInfo> participants,
                                             @JsonProperty("errors") final List<ErrorResponse> errors) {
        return new ParticipantResponse(participants, errors);
    }

    public List<ParticipantInfo> getParticipants() {
        return ImmutableList.copyOf(participants);
    }

    public List<ErrorResponse> getErrors() {
        return ImmutableList.copyOf(errors);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final ParticipantResponse that = (ParticipantResponse) o;
        return Objects.equals(participants, that.participants) &&
                Objects.equals(errors, that.errors);
    }

    @Override
    public int hashCode() {
        return Objects.hash(participants, errors);
    }

    @Override
    public String toString() {
        return "ParticipantResponse{" +
                "participants=" + participants +
                ", errors=" + errors +
                '}';
    }
}
