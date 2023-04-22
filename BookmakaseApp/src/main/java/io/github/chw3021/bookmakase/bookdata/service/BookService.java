package io.github.chw3021.bookmakase.bookdata.service;

import org.springframework.stereotype.Service;

import io.github.chw3021.bookmakase.bookdata.client.InterparkClient;
import io.github.chw3021.bookmakase.bookdata.dto.InterparkResponseDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional
public class BookService {
    private final InterparkClient interparkClient;

    public InterparkResponseDto getPopluarBooks(int categoryId) {
        return interparkClient.getPopularBooks(categoryId);
    }
    public InterparkResponseDto getRecommendedBooks(int categoryId) {
        return interparkClient.getRecommendedBooks(categoryId);
    }
    public InterparkResponseDto getNewBooks(int categoryId) {
        return interparkClient.getNewBooks(categoryId);
    }
    public InterparkResponseDto getBookSearchResults(String query) {
        return interparkClient.getBookSearchResults(query);
        
    }
}