package com.example.nodess.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.concurrent.TimeUnit;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {
    ApplicationConfiguration app;

    public WebConfiguration(ApplicationConfiguration applicationConfiguration) {
        this.app = applicationConfiguration;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:./"+app.getUploadPath()+"/")
                .setCacheControl(CacheControl.maxAge(30, TimeUnit.DAYS));
    }
}
