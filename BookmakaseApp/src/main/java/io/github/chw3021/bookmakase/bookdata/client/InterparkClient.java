package io.github.chw3021.bookmakase.bookdata.client;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import io.github.chw3021.bookmakase.bookdata.dto.InterparkResponseDto;
import io.github.chw3021.bookmakase.bookdata.properties.InterparkProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.fasterxml.jackson.databind.ObjectMapper;

@RequiredArgsConstructor
@Slf4j
@Component
public class InterparkClient {
    private final InterparkProperties properties;
    private final WebClient webClient;
    private ObjectMapper objectMapper = new ObjectMapper();

    public InterparkResponseDto getPopularBooks(int category_num) {//베스트셀러
        InterparkResponseDto interparkResponseDto = convertToResponse(getPopularBooksFromApi(category_num));
        return interparkResponseDto;
    }

    private String getPopularBooksFromApi(int categoryId) {
        String items = null;
        try {
            items = webClient.get()
                    .uri(builder -> builder.path("/bestSeller.api")
                            .queryParam("categoryId", categoryId)
                            .queryParam("output", "json")
                            .queryParam("key", properties.getKey()).build())
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(String.class).block();
        } catch (Exception e) {
            log.info(e.getMessage());
        }
        return items;
    }
    
    

    public InterparkResponseDto getRecommendedBooks(int category_num) {//추천도서
        InterparkResponseDto interparkResponseDto = convertToResponse(getRecommendedBooksFromApi(category_num));
        return interparkResponseDto;
    }

    private String getRecommendedBooksFromApi(int categoryId) {
        String items = null;
        try {
            items = webClient.get()
                    .uri(builder -> builder.path("/recommend.api")
                            .queryParam("categoryId", categoryId)
                            .queryParam("output", "json")
                            .queryParam("key", properties.getKey()).build())
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(String.class).block();
        } catch (Exception e) {
            log.info(e.getMessage());
        }
        return items;
    }

    

    public InterparkResponseDto getNewBooks(int category_num) {//신간도서
        InterparkResponseDto interparkResponseDto = convertToResponse(getNewBooksFromApi(category_num));
        return interparkResponseDto;
    }

    private String getNewBooksFromApi(int categoryId) {
        String items = null;
        try {
            items = webClient.get()
                    .uri(builder -> builder.path("/newBook.api")
                            .queryParam("categoryId", categoryId)
                            .queryParam("output", "json")
                            .queryParam("key", properties.getKey()).build())
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(String.class).block();
        } catch (Exception e) {
            log.info(e.getMessage());
        }
        return items;
    }
    


    public InterparkResponseDto getBookSearchResults(String query) {//도서 검색
        InterparkResponseDto interparkResponseDto = convertToResponse(getBookSearchResultsFromApi(query));
        return interparkResponseDto;
    }

    private String getBookSearchResultsFromApi(String query) {
        String items = null;
        try {
            items = webClient.get()
                    .uri(builder -> builder.path("/search.api")
                            .queryParam("query", query)
                            .queryParam("output", "json")
                            .queryParam("key", properties.getKey()).build())
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(String.class).block();
        } catch (Exception e) {
            log.info(e.getMessage());
        }
        return items;
    }
    
    
    
    
    
    private InterparkResponseDto convertToResponse(String textData) {
        InterparkResponseDto interparkResponseDto = new InterparkResponseDto();
        try {
            interparkResponseDto = objectMapper.readValue(textData, InterparkResponseDto.class);
        } catch (Exception e) {
            log.info(e.getMessage());
        }
        return interparkResponseDto;
    }
}