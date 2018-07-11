package com.example.demotest.steps;

import cucumber.api.java8.En;
import io.restassured.response.Response;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class ActuatorSteps implements En {

    private Response response;

    public ActuatorSteps() {
        When("^I ask for its health$", () -> {
            response = given().auth().basic("user", "password").get("/actuator/health");
        });

        Then("^its status should be UP$", () -> {
            response.then().body("status", is("UP"));
        });
    }
}
