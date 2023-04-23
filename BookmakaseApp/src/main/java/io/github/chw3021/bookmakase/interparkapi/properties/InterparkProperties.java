package io.github.chw3021.bookmakase.interparkapi.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@ConfigurationProperties(prefix = "interpark")
@Data
public class InterparkProperties {
	private String key;
}
