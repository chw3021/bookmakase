package io.github.chw3021.bookmakase.bookdata.domain;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@Builder @AllArgsConstructor @NoArgsConstructor
public class BookDto {
    private String title;
    private String link;
    private String language;
    private String copyright;
    private String pubDate;
    private String imageUrl;
    private String queryType;
    private String returnMessage;
    private int totalResults;
    private int startIndex;
    private int itemsPerPage;
    private int maxResults;
    private String query;
    private Long searchCategoryId;
    private String searchCategoryName;
    private int returnCode;
    
    
    private List<Book> item;


    public List<Book> getItem() {
        return item;
    }

    public void setItem(List<Book> item) {
        this.item = item;
    }
}