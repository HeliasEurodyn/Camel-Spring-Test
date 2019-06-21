package com.flexi;

import org.apache.camel.component.servlet.CamelHttpTransportServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@SpringBootApplication
public class FlexiCamelApplication {

    public static void main(String[] args) {
        SpringApplication.run(FlexiCamelApplication.class, args);
    }


    @Bean
    ServletRegistrationBean servletRegistrationBean() {
        final ServletRegistrationBean servlet = new ServletRegistrationBean(
                new CamelHttpTransportServlet(), "/flexirest/*");
        servlet.setName("CamelServlet");
        return servlet;
    }


}

