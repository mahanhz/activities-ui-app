package com.amhzing.activities.ui.smoketest;

import com.amhzing.activities.ui.ActivitiesUIApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ActivitiesUIApplication.class)
public class SmokeTest {

    @Value("${server.base-uri}")
    private String baseUri;

    @Value("${management.port}")
    private int managementPort;

    @Value("${management.context-path}")
    private String managementContextPath;

    private TestRestTemplate testRestTemplate = new TestRestTemplate();

    @Test
    public void healthStatus() {
        final ResponseEntity<Map> entity = testRestTemplate.getForEntity(getUrl(), Map.class);

        assertEquals(HttpStatus.OK, entity.getStatusCode());

        final Map body = entity.getBody();
        assertTrue(body.containsKey("status"));
        assertEquals(body.get("status"), "UP");
    }

    private String getUrl() {
        return baseUri + ":" + managementPort + "/" + managementContextPath + "/health";
    }
}