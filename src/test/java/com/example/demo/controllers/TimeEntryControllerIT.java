package com.example.demo.controllers;

import com.example.demo.model.DemoError;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TimeEntryControllerIT {

    @LocalServerPort
    private String port;
    private TestRestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() throws Exception {
        RestTemplateBuilder builder = new RestTemplateBuilder()
                .rootUri("http://localhost:" + port)
                .basicAuthorization("user", "password"); // parameters are comming from SecurityConfiguration

        restTemplate = new TestRestTemplate(builder);
    }

    @Test
    public void testNoSuchElementException() throws Exception {
        ResponseEntity<String> response = restTemplate.getForEntity("/time-entries/100000", String.class);
        DemoError error = objectMapper.readValue(response.getBody(), DemoError.class);
        assertAll(
                () -> assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND),
                () -> assertThat(error.getMessage()).isEqualTo("No such time entry")
        );
    }
}
