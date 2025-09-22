package io.microservices_java.currency.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.variables")
@Data
public class AppProperties {
    String url;
}
