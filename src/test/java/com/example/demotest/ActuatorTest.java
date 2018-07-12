package com.example.demotest;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

import static io.restassured.RestAssured.basic;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "classpath:actuator.feature"
)
public class ActuatorTest {

    @BeforeClass
    public static void initRestAssured() {
        RestAssured.baseURI = System.getProperty("baseURI", System.getenv("baseURI"));
        String port = System.getProperty("port", System.getenv("PORT"));
        if (port != null) {
            RestAssured.port = Integer.valueOf(port);
        }
        RestAssured.defaultParser = Parser.JSON;
        RestAssured.useRelaxedHTTPSValidation();
        RestAssured.authentication = basic(
                (System.getProperty("appUser", System.getenv("appUser"))),
                (System.getProperty("appUserPassword", System.getenv("appUserPassword"))));

    }

}
