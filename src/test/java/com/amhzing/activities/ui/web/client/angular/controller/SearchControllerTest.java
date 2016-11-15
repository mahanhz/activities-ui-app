package com.amhzing.activities.ui.web.client.angular.controller;

import com.amhzing.activities.ui.annotation.TestOffline;
import com.amhzing.activities.ui.domain.participant.model.*;
import com.amhzing.activities.ui.web.client.adapter.ParticipantAdapter;
import com.amhzing.activities.ui.web.client.model.ParticipantModel;
import com.google.common.collect.ImmutableList;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import net.minidev.json.JSONArray;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static com.amhzing.activities.ui.user.UserStore.USER_ANGULAR;
import static com.amhzing.activities.ui.web.MediaType.APPLICATION_JSON_V1;
import static com.amhzing.activities.ui.web.client.adapter.ParticipantFactory.adaptParticipant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(SearchController.class)
@TestOffline
public class SearchControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ParticipantAdapter participantAdapter;

    @Test
    public void should_get_access_details() throws Exception {
        given(participantAdapter.participants(any())).willReturn(participantModel());

        final ResultActions result = this.mvc.perform(get("/angular/search?country=se").with(user(USER_ANGULAR.getName()).password(USER_ANGULAR.getPassword()))
                                                                                       .accept(APPLICATION_JSON_V1))
                                             .andExpect(status().isOk())
                                             .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON_V1));

        final String content = result.andReturn().getResponse().getContentAsString();
        final Object document = Configuration.defaultConfiguration().jsonProvider().parse(content);

        assertParticipants(document);
        assertEmptyError(document);
    }

    private void assertEmptyError(final Object document) {
        final String error = JsonPath.read(document, "@.errorMessage");
        assertThat(error).isEmpty();
    }

    private void assertParticipants(final Object document) {
        final JSONArray participants = JsonPath.read(document, "@.participants");
        assertThat(participants).hasSize(1);

        final String participantId = JsonPath.read(document, "@.participants[0].participantId");
        assertThat(participantId).isEqualToIgnoringCase(participant().getParticipantId());

        final String firstName = JsonPath.read(document, "@.participants[0].name.firstName");
        assertThat(firstName).isEqualToIgnoringCase(name().getFirstName());

        final String lastName = JsonPath.read(document, "@.participants[0].name.lastName");
        assertThat(lastName).isEqualToIgnoringCase(name().getLastName());

        final String addressLine1 = JsonPath.read(document, "@.participants[0].address.addressLine1");
        assertThat(addressLine1).isEqualToIgnoringCase(address().getAddressLine1());

        final String city = JsonPath.read(document, "@.participants[0].address.city");
        assertThat(city).isEqualToIgnoringCase(address().getCity());

        final String country = JsonPath.read(document, "@.participants[0].address.country");
        assertThat(country).isEqualToIgnoringCase(address().getCountry().getCode());
    }

    private List<ParticipantModel> participantModel() {
        return adaptParticipant(participants());
    }

    private List<Participant> participants() {
        return ImmutableList.of(participant());
    }

    private Participant participant() {
        return Participant.create("pId", name(), address(), contactNumber(), email());
    }

    private Address address() {
        return Address.create("ad1", "ad2", "city", "pCode", Country.create("SE", ""));
    }

    private Name name() {
        return Name.create("fname", "mName", "lName", "II");
    }

    private ContactNumber contactNumber() {
        return ContactNumber.create("12345678");
    }

    private Email email() {
        return Email.create("test@example.com");
    }
}