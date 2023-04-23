package io.github.chw3021.bookmakase.bookdata.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@Builder @AllArgsConstructor @NoArgsConstructor
public class Book {
    private String title;
    private String author;
    private int categoryId;
    private String publisher;
    private String pubDate;
    private String cover;
    private String description;
    private String link;
    
    private String[] keywords;//크롤링후 검색결과 저장

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Integer getCategoryId() {
    	return categoryId;
    }
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	

    public String[] getKeywords() {
    	return keywords;
    }
	public void setKeywords(String[] keywords) {
		this.keywords = keywords;
	}
}