package com.example.demo.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.assertj.core.api.BDDAssertions.then;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GreetingsControllerIT {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void readyForPalGreeting() throws Exception {
        // when
        ResponseEntity<String> response = restTemplate.getForEntity("/greeting", String.class);
        // then
        assertAll(
                () -> assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK),
                () -> assertThat(response.getBody()).isEqualTo("I'm ready for PAL"));
    }

    /***
     * Test that environment variable properly set through maven failsafe plugin
     */
    @Test
    public void readEnvironmentVariable() {
        String environmentVariableValue = System.getenv("SPRING_DATASOURCE_URL");

        assertThat(environmentVariableValue).isNotNull().isNotEmpty();
    }

    @Test
    public void shouldReturnBuildAndEasterEggInformationWhenSendingRequestToManagementEndpoint() throws Exception {
        @SuppressWarnings("rawtypes")
        ResponseEntity<Map> entity = this.restTemplate.getForEntity(
                "/actuator/info", Map.class);

        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.getBody().get("build")).isNotNull();
        then(entity.getBody().get("easter_egg")).isNotNull();
    }

    @Test
    public void shouldReturnHealthInformationWhenSendingRequestToManagementEndpoint() throws Exception {
        @SuppressWarnings("rawtypes")
        ResponseEntity<Map> entity = this.restTemplate.getForEntity(
                "/actuator/health", Map.class);

        if (entity.getBody().get("details") == null) {
            throw new Exception("Invalid health result format");
        }

        LinkedHashMap<String, LinkedHashMap> details = (LinkedHashMap)entity.getBody().get("details");
        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(details.get("timeEntry")).isNotNull();
    }
}
