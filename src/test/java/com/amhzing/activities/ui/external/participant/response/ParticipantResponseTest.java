package com.amhzing.activities.ui.external.participant.response;

import com.amhzing.activities.ui.annotation.TestOffline;
import com.amhzing.activities.ui.external.participant.domain.ParticipantInfo;
import com.amhzing.activities.ui.helper.JsonLoader;
import com.google.common.collect.ImmutableList;
import com.jayway.jsonpath.JsonPath;
import net.minidev.json.JSONArray;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

import static com.amhzing.activities.ui.helper.ExternalParticipantHelper.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasEntry;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@JsonTest
@TestOffline
public class ParticipantResponseTest {

    @Autowired
    private JacksonTester<ParticipantResponse> json;

    @Autowired
    private ResourceLoader resourceLoader;

    private JsonLoader jsonLoader;

    @Before
    public void setUp() throws Exception {
        jsonLoader = new JsonLoader();
    }

    @Test
    public void should_serialize_response() throws Exception {
        final ParticipantResponse response = ParticipantResponse.create(ImmutableList.of(participantInfo()),
                                                                                  ImmutableList.of(errorResponse()));

        final JsonContent<ParticipantResponse> jsonContent = this.json.write(response);

        assertParticipants(jsonContent.getJson());
        assertErrorWithCorrelationId(jsonContent.getJson());
    }

    private void assertParticipants(final String document) {
        final JSONArray participants = JsonPath.read(document, "@.participants");
        assertEquals(participants.size(), 1);

        final Map<String, ?> participant = (Map<String, String>) participants.get(0);
        assertThat(participant, hasEntry("participantId", participantId()));
        assertThat((Map<String, String>) participant.get("name"), hasEntry("lastName", name().getLastName()));

        final Map<String, ?> address = (Map<String, String>) participant.get("address");
        assertThat(address, hasEntry("addressLine1", address().getAddressLine1()));
        assertThat((Map<String, String>) address.get("country"), hasEntry("code", address().getCountry().getCode()));

        assertThat((Map<String, String>) participant.get("contactNumber"), hasEntry("value", contactNumber().getValue()));
        assertThat((Map<String, String>) participant.get("email"), hasEntry("value", email().getValue()));
    }

    private void assertErrorWithCorrelationId(final String document) {
        final JSONArray errors = JsonPath.read(document, "@.errors");
        assertEquals(errors.size(), 1);

        final Map<String, String> error = (Map<String, String>) errors.get(0);
        assertThat(error, hasEntry("code", errorCode()));
        assertThat(error, hasEntry("message", errorMessage()));
        assertThat(error, hasEntry("correlationId", correlationId()));
    }

    private ParticipantInfo participantInfo() {
        return ParticipantInfo.create(participantId(),
                                      name(),
                                      address(),
                                      contactNumber(),
                                      email());
    }

    private String participantId() {
        return "12345";
    }

    private String correlationId() {
        return "19837";
    }

    private String errorCode() {
        return "errC";
    }

    private String errorMessage() {
        return "errMsg";
    }

    private ErrorResponse errorResponse() {
        return ErrorResponse.create(errorCode(), errorMessage(), correlationId());
    }
}