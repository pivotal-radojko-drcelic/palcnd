package com.example.demo.actuator;

import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

/***
 * This class demonstrate how to add custom information to /actuator/info endpoint result
 */
@Component
public class EasterEggInfo implements InfoContributor {
    @Override
    public void contribute(Info.Builder builder) {
        builder.withDetail("easter_egg", "Greetings from Demo app!");
        builder.build();
    }
}
