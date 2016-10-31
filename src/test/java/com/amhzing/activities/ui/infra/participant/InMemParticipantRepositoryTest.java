package com.amhzing.activities.ui.infra.participant;

import com.amhzing.activities.ui.domain.participant.repository.QueryCriteria;
import io.atlassian.fugue.Either;
import org.junit.Before;
import org.junit.Test;

import static io.atlassian.fugue.Either.right;
import static org.assertj.core.api.Assertions.assertThat;

public class InMemParticipantRepositoryTest {

    private InMemParticipantRepository repository;

    @Before
    public void before() {
        repository = new InMemParticipantRepository();
    }

    @Test
    public void should_get_participants() throws Exception {
        final Either<CorrelatedFailure, Participants> result = repository.participantsByCriteria(queryCriteria("SE"));

        assertThat(result.isRight()).isEqualTo(true);
        assertThat(result).isEqualTo(right(repository.participants()));
    }

    private QueryCriteria queryCriteria(final String country) {
        return QueryCriteria.create(country, "", "", "", "");
    }
}