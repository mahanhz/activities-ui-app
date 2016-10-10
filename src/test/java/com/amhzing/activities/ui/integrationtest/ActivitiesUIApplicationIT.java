package com.amhzing.activities.ui.integrationtest;

import com.amhzing.activities.ui.ActivitiesUIApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.autoconfigure.LocalManagementPort;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ActivitiesUIApplication.class,
                webEnvironment = WebEnvironment.RANDOM_PORT)
public class ActivitiesUIApplicationIT {

    @LocalServerPort
    private int port = 0;

    @LocalManagementPort
    private int managementPort = 0;

    @Value("${server.context-path}")
    private String serverContextPath;

    @Value("${management.context-path}")
    private String managementContextPath;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void configurationAvailable() {
        final ResponseEntity<String> entity = testRestTemplate.getForEntity(
                "http://localhost:" + port + serverContextPath + "/config-message", String.class);

        assertEquals(HttpStatus.OK, entity.getStatusCode());
    }

    @Test
    public void uiRedirectsToLogin() {
        final ResponseEntity<String> entity = testRestTemplate.getForEntity(
                "http://localhost:" + port + serverContextPath + "/vaadin", String.class);

        assertEquals(HttpStatus.FOUND, entity.getStatusCode());
    }

    @Test
    public void managementProtected() {
        ResponseEntity<Map> entity = testRestTemplate.getForEntity(
                "http://localhost:" + managementPort + managementContextPath, Map.class);

        assertEquals(HttpStatus.UNAUTHORIZED, entity.getStatusCode());
    }
}