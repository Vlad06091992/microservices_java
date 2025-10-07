package io.microservices_java.currency;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@ConfigurationPropertiesScan
@EnableDiscoveryClient
public class CurrencyApplication {

    public static void main(String[] args) {
        SpringApplication.run(CurrencyApplication.class, args);
    }

}

