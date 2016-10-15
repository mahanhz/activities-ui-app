package com.amhzing.activities.ui.integrationtest;

import com.amhzing.activities.ui.ActivitiesUIApplication;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static com.amhzing.activities.ui.configuration.AuthenticationConfig.VAADIN_USER;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.logout;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ActivitiesUIApplication.class, webEnvironment = RANDOM_PORT)
public class SecurityIT {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                                 .apply(springSecurity())
                                 .build();
    }

    @Test
    public void should_redirect_anonymous_user() throws Exception {
        mockMvc.perform(get("/")).andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockUser(roles = "HACKER")
    public void should_deny_invalid_vaadin_user() throws Exception {
        mockMvc.perform(get("/vaadin")).andExpect(status().is4xxClientError());
    }

    @Test
    @WithMockUser(roles = VAADIN_USER)
    public void should_allow_valid_vaadin_user() throws Exception {
        mockMvc.perform(get("/vaadin")).andExpect(status().isOk());
    }

    @Test
    public void should_allow_anonymous_config_message_access() throws Exception {
        mockMvc.perform(get("/config-message")).andExpect(status().isOk());
    }

    @Test
    public void should_deny_invalid_login() throws Exception {
        mockMvc.perform(formLogin()).andExpect(unauthenticated());
    }

    @Test
    public void should_allow_valid_login() throws Exception {
        mockMvc.perform(formLogin().user("vaadin").password("p")).andExpect(authenticated());
    }

    @Test
    public void should_logout() throws Exception {
        mockMvc.perform(logout()).andExpect(status().is3xxRedirection());
    }
}
