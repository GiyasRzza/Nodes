package com.example.nodess.configuration;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@EnableConfigurationProperties
public class ApplicationConfiguration {

    @Value(value = "${upload-path}")
    private String uploadPath;
}
