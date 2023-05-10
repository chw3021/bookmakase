package io.github.chw3021.bookmakase.bookdata.domain;

import java.time.LocalDate;
import java.util.Objects;

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
    private String pubDate;
    private String cover;
    private String description;
    private String url;
    
    private String translator;
    private String link;
    private String mobileLink;
    private String additionalLink;

    private String isbn;
    private int reviewCount;
    private int priceStandard;
    private int priceSales;
    private int discountRate;
    private String saleStatus;
    private int rank;
    private int mileage;
    private int mileageRate;
    private int customerReviewRank;
    private String coverSmallUrl;
    private String coverLargeUrl;
    private String categoryName;
    
    
    @Id
    @Column (name = "itemid")
    private Long itemId;
    //Id(PK변경)

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(id, book.id) &&
                Objects.equals(itemId, book.itemId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, itemId);
    }
	
}