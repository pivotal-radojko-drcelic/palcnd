package com.example.demo.security;

import com.example.demo.DemoApplication;
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
import org.springframework.test.context.junit4.SpringRunner;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(SpringExtension.class) // Integration tests with JUnit5 require this instead @RunWith
@SpringBootTest(classes = DemoApplication.class, webEnvironment = RANDOM_PORT)
public class SecurityTest {

    @LocalServerPort
    private String port;
    private TestRestTemplate authorizedRestTemplate;

    @Autowired
    private TestRestTemplate unAuthorizedRestTemplate;

    @BeforeEach
    public void setUp() throws Exception {
        RestTemplateBuilder builder = new RestTemplateBuilder()
                .rootUri("http://localhost:" + port)
                .basicAuthorization("user", "password");

        authorizedRestTemplate = new TestRestTemplate(builder);
    }

    @Test
    public void unauthorizedTest() {
        ResponseEntity<String> response = this.unAuthorizedRestTemplate.getForEntity("/time-entries", String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    public void authorizedTest() {
        ResponseEntity<String> response = this.authorizedRestTemplate.getForEntity("/time-entries", String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
