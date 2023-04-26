package io.github.chw3021.bookmakase.bookdata.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.chw3021.bookmakase.bookdata.domain.Book;
import io.github.chw3021.bookmakase.bookdata.repository.BookRepository;
import io.github.chw3021.bookmakase.interparkapi.client.InterparkClient;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class BookCrawling {
    @Autowired
    private InterparkClient interparkClient;
    @Autowired
    private BookRepository bookRepository;

    public void crawlAllBooks() {
        // 모든 도서 데이터를 받아올 검색 조건을 정의합니다.
        String[] keywords = { "소설", "역사", "과학", "인문", "예술", "종교" };
        int[] categories = { 11, 15, 22, 23, 26, 34, 38, 39, 41, 45, 46, 53, 54, 55, 61, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99 };

        // 검색 조건을 조합해서 API 호출을 반복해서 수행합니다.
        for (String keyword : keywords) {
            for (int categoryId : categories) {
                List<Book> books = interparkClient.searchBooks(keyword, categoryId);
                saveSearchResult(keywords, categoryId, books);
            }
        }
    }
    
    private void saveSearchResult(String[] keywords, int categoryId, List<Book> books) {
        // 검색 결과를 도서 저장소에 저장합니다.
        for (Book book : books) {
            book.setKeywords(keywords);
            book.setCategoryId(categoryId);
            bookRepository.save(book);
        }
    }
}