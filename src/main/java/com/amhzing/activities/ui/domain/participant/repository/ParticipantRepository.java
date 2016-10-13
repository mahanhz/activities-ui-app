package com.amhzing.activities.ui.domain.participant.repository;

import io.atlassian.fugue.Either;

public interface ParticipantRepository<L, R> {

    Either<L, R> participantsByCriteria(final QueryCriteria queryCriteria);
}
