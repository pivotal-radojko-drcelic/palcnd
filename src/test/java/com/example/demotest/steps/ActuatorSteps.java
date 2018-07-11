package com.example.demotest.steps;

import cucumber.api.java8.En;
import io.restassured.response.Response;

import static io.restassured.RestAssured.get;
import static org.hamcrest.Matchers.is;

public class ActuatorSteps implements En {

    private Response response;

    public ActuatorSteps() {
        When("^I ask for its health$", () -> {
            response = get("/health");
        });

        Then("^its status should be UP$", () -> {
            response.then().body("status", is("UP"));
        });
    }
}
