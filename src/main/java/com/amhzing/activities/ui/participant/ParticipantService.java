package com.amhzing.activities.ui.participant;

import io.atlassian.fugue.Either;

public interface ParticipantService<L, R> {

    Either<L, R> participantsByCriteria(final QueryCriteria queryCriteria);
}
