package io.github.chw3021.bookmakase.goal.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookGoalDto {
    
    private Long memberId;

    private String goalname;
    private Integer categoryId;
    private Integer targetQuantity;
    private Integer readed;
    private LocalDate startDate;
    private LocalDate endDate;
    private Boolean completed;

}
