package com.ll.sbb.Market;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class MarketForm {
    @NotEmpty(message = "제목 입력은 필수항목입니다.")
    @Size(max = 200)
    private String subject;

    @NotEmpty(message = "내용 입력은 필수항목입니다.")
    private String content;

    @NotEmpty(message = "구입 가격 입력은 필수항목입니다.(숫자만 입력 해주세요.)")
    private Integer price;

}