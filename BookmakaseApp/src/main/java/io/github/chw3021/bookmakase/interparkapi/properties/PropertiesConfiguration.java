package io.github.chw3021.bookmakase.interparkapi.properties;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@EnableConfigurationProperties({
        InterparkProperties.class
})
@PropertySource({
        "classpath:properties/interpark.properties"
})
public class PropertiesConfiguration {
}