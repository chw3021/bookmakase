package io.github.chw3021.bookmakase.interparkapi.client;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.ArrayList;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import io.github.chw3021.bookmakase.bookdata.domain.Book;
import io.github.chw3021.bookmakase.bookdata.domain.BookDto;
import io.github.chw3021.bookmakase.interparkapi.properties.InterparkProperties;
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

    public BookDto getPopularBooks(int category_num) {//베스트셀러
        return convertToResponse(getPopularBooksFromApi(category_num));
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
    
    

    public BookDto getRecommendedBooks(int category_num) {//추천도서
        return convertToResponse(getRecommendedBooksFromApi(category_num));
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

    

    public BookDto getNewBooks(int category_num) {//신간도서
        return convertToResponse(getNewBooksFromApi(category_num));
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


    public BookDto getBookSearchResultsByItemId(Long itemId) {//도서 검색
        return convertToResponse(getBookSearchResultsByItemIdFromApi(itemId));
    }

    private String getBookSearchResultsByItemIdFromApi(Long itemId) {
        String items = null;
        try {
            items = webClient.get()
                    .uri(builder -> builder.path("/search.api")
                            .queryParam("queryType", "productNumber")
                            .queryParam("query", itemId)
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


    public BookDto getBookSearchResults(String query) {//도서 검색
        return convertToResponse(getBookSearchResultsFromApi(query));
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
    
    
    
    private BookDto convertToResponse(String textData) {
        BookDto interparkResponseDto = new BookDto();
        try {
            interparkResponseDto = objectMapper.readValue(textData, BookDto.class);
        } catch (Exception e) {
            log.info(e.getMessage());
        }
        return interparkResponseDto;
    }
    
    
    public List<Book> searchBooks(String keyword, int categoryId) {
        List<Book> allBooks = new ArrayList<>();
        AtomicInteger currentPage = new AtomicInteger(1);
        while (true) {
            String items = null;
            try {
                items = webClient.get()
                        .uri(builder -> builder.path("/search.api")
                                .queryParam("query", keyword)
                                .queryParam("queryType", "author")
                                .queryParam("categoryId", categoryId)
                                .queryParam("output", "json")
                                .queryParam("start", currentPage.getAndIncrement())
                                .queryParam("maxResults", 100)
                                .queryParam("key", properties.getKey()).build())
                        .accept(MediaType.APPLICATION_JSON)
                        .retrieve()
                        .bodyToMono(String.class).block();
            } catch (Exception e) {
                log.info(e.getMessage());
            }
            BookDto bookDto = convertToResponse(items);
            List<Book> books = bookDto.getItem();
            if (books == null || books.isEmpty()) {
                break; // 더 이상 결과가 없으면 종료
            }
            allBooks.addAll(books);

            try {
                Thread.sleep(20); // 일정 시간 대기
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return allBooks;
    }
}