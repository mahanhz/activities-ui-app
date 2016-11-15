package com.amhzing.activities.ui.web.client.angular.controller;

import com.amhzing.activities.ui.annotation.TestOffline;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static com.amhzing.activities.ui.user.UserStore.USER_ANGULAR;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(MainController.class)
@TestOffline
public class MainControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void should_redirect_to_index() throws Exception {

        this.mvc.perform(get("/angular").with(user(USER_ANGULAR.getName()).password(USER_ANGULAR.getPassword())))
                .andExpect(status().is3xxRedirection());
    }
}