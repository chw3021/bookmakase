package io.github.chw3021.bookmakase.interparkapi.dto;

import java.util.List;

import io.github.chw3021.bookmakase.bookdata.dto.Book;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@Builder @AllArgsConstructor @NoArgsConstructor
public class InterparkResponseDto {
    private String title;
    private List<Book> item;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Book> getItem() {
        return item;
    }

    public void setItem(List<Book> item) {
        this.item = item;
    }
}