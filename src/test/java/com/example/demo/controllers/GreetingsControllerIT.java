package com.example.demo.controllers;

import com.jayway.jsonpath.DocumentContext;
import org.assertj.core.api.JUnitSoftAssertions;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.LinkedHashMap;
import java.util.Map;

import static com.jayway.jsonpath.JsonPath.parse;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.assertj.core.api.BDDAssertions.then;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GreetingsControllerIT {

    // If don't want JUnit 5 then can use soft assertions from AssertJ
    @Rule
    public final JUnitSoftAssertions softly = new JUnitSoftAssertions();

    @LocalServerPort
    private String port;
    private TestRestTemplate restTemplate;

    @Before
    public void setup() throws Exception {
        RestTemplateBuilder builder = new RestTemplateBuilder()
                .rootUri("http://localhost:" + port)
                .basicAuthorization("user", "password"); // parameters are comming from SecurityConfiguration

        restTemplate = new TestRestTemplate(builder);
    }

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

        assertAll(
                () -> then(entity.getStatusCode()).isEqualTo(HttpStatus.OK),
                () -> then(entity.getBody().get("build")).isNotNull(),
                () -> then(entity.getBody().get("easter_egg")).isNotNull());
    }

    @Test
    public void shouldReturnHealthInformationWhenSendingRequestToManagementEndpoint() throws Exception {
        @SuppressWarnings("rawtypes")
        ResponseEntity<String> response = this.restTemplate.getForEntity(
                "/actuator/health", String.class);

        DocumentContext healthJson = parse(response.getBody());

        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        softly.assertThat(healthJson.read("$.status", String.class)).isEqualTo("UP");
        softly.assertThat(healthJson.read("$.details.db.status", String.class)).isEqualTo("UP");
        softly.assertThat(healthJson.read("$.details.diskSpace.status", String.class)).isEqualTo("UP");
        softly.assertThat(healthJson.read("$.details.timeEntry.status", String.class)).isEqualTo("UP");
    }
}
