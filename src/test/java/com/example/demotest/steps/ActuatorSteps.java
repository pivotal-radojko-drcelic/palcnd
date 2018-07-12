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
            String userName = System.getProperty("appUser", System.getenv("appUser"));
            String userPassword = System.getProperty("appUserPassword", System.getenv("appUserPassword"));
            response = given().auth().basic(userName, userPassword).log().all().get("/actuator/health");
        });

        Then("^its status should be UP$", () -> {
            response.then().log().all().body("status", is("UP"));
        });
    }
}
