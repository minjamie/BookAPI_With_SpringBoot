package com.example.bookAPI.dto.memberBook;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MemberBookRequestDto {
    @ApiModelProperty(value = "책 아이디", example = "1")
    private Long bookId;

    @ApiModelProperty(value = "가지고 있는지 여부", example = "true")
    private boolean isHas;

    @ApiModelProperty(value = "읽은 상태 여부", example = "false")
    private boolean isRead;
}