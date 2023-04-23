package io.github.chw3021.bookmakase.interparkapi.properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
public class InterparkConfiguration {

    @Bean
    public WebClient interparkWebClient() {
        return WebClient.builder()
                .baseUrl("http://book.interpark.com/api").build();
    }
}