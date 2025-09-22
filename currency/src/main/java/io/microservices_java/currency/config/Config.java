package io.microservices_java.currency.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

@Data
@ConfigurationProperties(prefix = "url")
public class Config {
    String url;
}
