package com.example.bookAPI.dto.review;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookReviewRequestDto {

    @NotNull
    @ApiModelProperty(value = "책 id", dataType = "Long", required = true, example = "86")
    private Long book_id;


    @NotBlank
    @Length(min = 10, max = 3000)
    @ApiModelProperty(value = "내용", dataType = "String", required = true, example = "책이 재밌어요")
    private String content;

    @NotNull
    @DecimalMin(value = "0.0")
    @DecimalMax(value = "5.0", inclusive = true)
    @ApiModelProperty(value = "평점", dataType = "BigDecimal", required = true, example = "4.5")
    private BigDecimal rating;
}
