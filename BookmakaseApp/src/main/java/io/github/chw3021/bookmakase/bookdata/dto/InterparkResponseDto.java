package io.github.chw3021.bookmakase.bookdata.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@Builder @AllArgsConstructor @NoArgsConstructor
public class InterparkResponseDto {
    private String title;
    private List<BookDto> item;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<BookDto> getItem() {
        return item;
    }

    public void setItem(List<BookDto> item) {
        this.item = item;
    }
}