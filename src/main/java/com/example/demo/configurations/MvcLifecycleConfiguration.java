package com.example.demo.configurations;

import com.example.demo.logging.TimeEntryRequestInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcLifecycleConfiguration implements WebMvcConfigurer {

    @Autowired
    private TimeEntryRequestInterceptor timeEntryRequestInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(timeEntryRequestInterceptor)
                .addPathPatterns("/**/time-entries/**/");
    }
}
