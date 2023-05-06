package io.github.chw3021.bookmakase.bookdata.domain;

import java.time.LocalDate;

//import io.github.chw3021.bookmakase.goal.domain.BookShelf;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@Builder @AllArgsConstructor @NoArgsConstructor
public class Book {


    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    //@GeneratedValue(strategy = GenerationType.IDENTITY) 이 옵션은 Db에서
    //순차적으로 값을 올리는 거라 고유 ID입력 안되니 수정할 것.
    private Long id;

    private String title;
    private String author;
    private int categoryId;
    private String publisher;
    private LocalDate pubDate;
    private String cover;
    private String description;
    private String link;

    @Id
    @Column (name = "book_id")
    private Long bookId;
    //Id(PK변경)

	
}