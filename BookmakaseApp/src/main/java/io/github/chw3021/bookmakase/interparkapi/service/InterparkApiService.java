package io.github.chw3021.bookmakase.interparkapi.service;

import org.springframework.stereotype.Service;

import io.github.chw3021.bookmakase.bookdata.domain.BookDto;
import io.github.chw3021.bookmakase.interparkapi.client.InterparkClient;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional
public class InterparkApiService {
    private final InterparkClient interparkClient;

    public BookDto getPopluarBooks(int categoryId) {
        return interparkClient.getPopularBooks(categoryId);
    }
    public BookDto getRecommendedBooks(int categoryId) {
        return interparkClient.getRecommendedBooks(categoryId);
    }
    public BookDto getNewBooks(int categoryId) {
        return interparkClient.getNewBooks(categoryId);
    }
    public BookDto getBookSearchResults(String query) {
        return interparkClient.getBookSearchResults(query);
        
    }
}