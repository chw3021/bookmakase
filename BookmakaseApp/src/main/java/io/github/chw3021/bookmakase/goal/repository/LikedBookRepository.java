package io.github.chw3021.bookmakase.goal.repository;

import io.github.chw3021.bookmakase.bookdata.domain.Book;
import io.github.chw3021.bookmakase.goal.domain.LikedBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikedBookRepository extends JpaRepository<LikedBook,Long>{
    List<LikedBook> findAllByMemberId(Long memberId);

    List<LikedBook> findAllByBook(Book book);
}
