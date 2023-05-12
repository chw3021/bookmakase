package io.github.chw3021.bookmakase.bookdata.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.github.chw3021.bookmakase.signservice.domain.Member;
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


    @Id
    @Column (name = "itemId")
    private Long itemId;
    //Id(PK변경)

    private String title;
    private String author;
    private int categoryId;
    private String publisher;
    private String pubDate;
    private String description;
    private String link;
    private String coverSmallUrl;
    private String saleStatus;
    private int priceStandard;

    @ManyToMany(mappedBy = "likedBooks")
    private List<Member> likedByMembers = new ArrayList<>();

    public void addlikedByMembers(Member member){
        boolean isAdded = this.likedByMembers.add(member);
        if(isAdded) {
            member.getLikedBooks().add(this);
        }
    }

    @Transient
    private String cover;
    @Transient
    private String url;

    @Transient
    private String translator;
    @Transient
    private String mobileLink;
    @Transient
    private String additionalLink;

    @Transient
    private String isbn;
    @Transient
    private int reviewCount;
    @Transient
    private int priceSales;
    @Transient
    private int discountRate;
    @Transient
    private int rank;
    @Transient
    private int mileage;
    @Transient
    private int mileageRate;
    @Transient
    private int customerReviewRank;
    @Transient
    private String coverLargeUrl;
    @Transient
    private String categoryName;
    

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