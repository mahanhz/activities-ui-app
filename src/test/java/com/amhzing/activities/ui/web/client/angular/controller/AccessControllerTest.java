package com.amhzing.activities.ui.web.client.angular.controller;

import com.amhzing.activities.ui.annotation.TestOffline;
import com.amhzing.activities.ui.web.client.adapter.UIAccessService;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static com.amhzing.activities.ui.user.UserStore.USER_ANGULAR;
import static com.amhzing.activities.ui.web.MediaType.APPLICATION_JSON_V1;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(AccessController.class)
@TestOffline
public class AccessControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UIAccessService uiAccessService;

    @Test
    public void should_get_access_details() throws Exception {
        given(uiAccessService.includeFacilitatedActivity()).willReturn(true);
        given(uiAccessService.includeHostedActivity()).willReturn(false);

        final ResultActions result = this.mvc.perform(get("/angular/access").with(user(USER_ANGULAR.getName()).password(USER_ANGULAR.getPassword()))
                                                                            .accept(APPLICATION_JSON_V1))
                                             .andExpect(status().isOk())
                                             .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON_V1));

        final String content = result.andReturn().getResponse().getContentAsString();
        final Object document = Configuration.defaultConfiguration().jsonProvider().parse(content);

        final Boolean includeFacilitatedActivity = JsonPath.read(document, "@.includeFacilitatedActivity");
        final Boolean includeHostedActivity = JsonPath.read(document, "@.includeHostedActivity");

        assertThat(includeFacilitatedActivity).isTrue();
        assertThat(includeHostedActivity).isFalse();
    }
}