package com.amhzing.activities.ui.integrationtest;

import com.amhzing.activities.ui.ActivitiesUIApplication;
import com.amhzing.activity.facilitated.FacilitatedActivityApplication;
import com.amhzing.activity.facilitated.query.data.participant.Failure;
import com.amhzing.activity.facilitated.query.data.participant.ParticipantService;
import com.amhzing.activity.facilitated.query.data.participant.QueryCriteria;
import com.amhzing.activity.facilitated.query.data.participant.mapping.Participants;
import io.atlassian.fugue.Either;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static com.amhzing.activities.ui.participant.Failure.SYSTEM_IS_DOWN;
import static com.amhzing.activity.facilitated.query.data.participant.Failure.SYSTEM_IS_DOWN;
import static com.netflix.config.ConfigurationManager.getConfigInstance;
import static io.atlassian.fugue.Either.left;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ActivitiesUIApplication.class,
                webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ExternalParticipantIT {

    @Autowired
    private ParticipantService<Failure, Participants> participantService;

    @Before
    public void init() {
        getConfigInstance().clear();
    }

    @Test
    public void should_get_participant_when_circuit_is_closed() throws Exception {
        givenTimeout(10000);

        final Either<Failure, Participants> result = participantService.participantsByCriteria(queryCriteria());

        assertTrue("Result was not right. It was: " + result, result.isRight());
    }

    @Test
    public void should_fallback_when_circuit_is_open() throws Exception {
        givenCircuitIsOpen();

        final Either<Failure, Participants> result = participantService.participantsByCriteria(queryCriteria());

        assertThat(result).isEqualTo(left(SYSTEM_IS_DOWN));
    }

    @Test
    public void should_fallback_when_timeout() throws Exception {
        givenTimeout(1);

        final Either<Failure, Participants> result = participantService.participantsByCriteria(queryCriteria());

        assertThat(result).isEqualTo(left(SYSTEM_IS_DOWN));
    }

    private void givenCircuitIsOpen() {
        getConfigInstance().setProperty("hystrix.command.default.circuitBreaker.forceOpen", true);
    }

    private void givenTimeout(final int timeout) {
        getConfigInstance().setProperty("hystrix.command.default.execution.isolation.thread.timeoutInMilliseconds", timeout);
    }

    private QueryCriteria queryCriteria() {
        return QueryCriteria.create("se", "", "", "", "");
    }
}
