package io.github.chw3021.bookmakase.bookdata.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.github.chw3021.bookmakase.bookdata.domain.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    // 도서명으로 검색하여 해당하는 책들을 반환하는 메서드
    List<Book> findByTitleContainingIgnoreCase(String title);

    // 출판사로 검색하여 해당하는 책들을 반환하는 메서드
    List<Book> findByPublisherContainingIgnoreCase(String publisher);

    // 카테고리 ID로 검색하여 해당하는 책들을 반환하는 메서드
    List<Book> findByCategoryId(int categoryId);

    // 출판일로 검색하여 해당하는 책들을 반환하는 메서드
    List<Book> findByPubDate(LocalDate publishDate);
}