package com.amhzing.activities.ui.integrationtest;

import com.amhzing.activities.ui.ActivitiesUIApplication;
import com.amhzing.activities.ui.domain.participant.repository.QueryCriteria;
import com.amhzing.activities.ui.infra.participant.CorrelatedFailure;
import com.amhzing.activities.ui.infra.participant.DefaultParticipantService;
import com.amhzing.activities.ui.infra.participant.Participants;
import io.atlassian.fugue.Either;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static com.amhzing.activities.ui.infra.participant.Failure.SYSTEM_IS_DOWN;
import static com.netflix.config.ConfigurationManager.getConfigInstance;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ActivitiesUIApplication.class,
                webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ExternalParticipantIT {

    @Autowired
    private DefaultParticipantService participantService;

    @Before
    public void init() {
        getConfigInstance().clear();
    }

    @Test
    public void should_get_participant_when_circuit_is_closed() throws Exception {
        givenTimeout(10000);

        final Either<CorrelatedFailure, Participants> result = participantService.participantsByCriteria(queryCriteria());

        assertThat(result).isNotNull();
        assertThat(result.isRight()).as("Result was not right. It was: %s", result).isTrue();
    }

    @Test
    public void should_fallback_when_circuit_is_open() throws Exception {
        givenCircuitIsOpen();

        final Either<CorrelatedFailure, Participants> result = participantService.participantsByCriteria(queryCriteria());

        assertThat(result).isNotNull();
        assertThat(result.isLeft()).as("Result was not left. It was: %s", result).isTrue();
        assertThat(result.left().get().getFailure()).isEqualTo(SYSTEM_IS_DOWN);
    }

    @Test
    public void should_fallback_when_timeout() throws Exception {
        givenTimeout(1);

        final Either<CorrelatedFailure, Participants> result = participantService.participantsByCriteria(queryCriteria());

        assertThat(result).isNotNull();
        assertThat(result.isLeft()).as("Result was not left. It was: %s", result).isTrue();
        assertThat(result.left().get().getFailure()).isEqualTo(SYSTEM_IS_DOWN);
    }

    private void givenCircuitIsOpen() {
        getConfigInstance().setProperty("hystrix.command.default.circuitBreaker.forceOpen", true);
    }

    private void givenTimeout(final int timeout) {
        getConfigInstance().setProperty("hystrix.command.default.execution.isolation.thread.timeoutInMilliseconds", timeout);
    }

    private QueryCriteria queryCriteria() {
        return QueryCriteria.create("us", "", "", "", "");
    }
}
