package com.example.demo.json;

import com.example.demo.model.TimeEntry;
import io.github.benas.randombeans.EnhancedRandomBuilder;
import io.github.benas.randombeans.api.EnhancedRandom;
import org.apache.tomcat.jni.Local;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.core.io.ClassPathResource;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class JsonConfigurationTest {
    private EnhancedRandom enhancedRandom;

    @Autowired
    private JacksonTester<TimeEntry> jsonTester;

    @BeforeEach
    public void setup() {
        enhancedRandom = EnhancedRandomBuilder.aNewEnhancedRandom();
    }
    @Test
    public void serializeToJson() throws Exception {
        // given
        TimeEntry dto = new TimeEntry(123L, 456L, LocalDate.parse(""), 100);
        ClassPathResource resource = new ClassPathResource("json/TimeEntry.json");
        // then
        assertThat(this.jsonTester.write(dto)).isEqualToJson(resource.getInputStream());
    }

    @Test
    public void deserializeJsonToExpenseDto() throws Exception {
        // given
        ClassPathResource resource = new ClassPathResource("json/TimeEntry.json");
        // when
        TimeEntry timeEntry = this.jsonTester.readObject(resource.getInputStream());
        // then
        assertAll(
                () -> assertThat(timeEntry.getHours()).isEqualTo(100),
                () -> assertThat(timeEntry.getDate()).isEqualTo(LocalDate.parse("22-03-2018")),
                () -> assertThat(timeEntry.getProjectId()).isEqualTo(123),
                () -> assertThat(timeEntry.getUserId()).isEqualTo(456));
    }
}
