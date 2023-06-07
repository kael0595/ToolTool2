package com.ll.sbb.MarketAnswer;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MarketAnswerForm {
    @NotEmpty(message = "내용은 필수 입니다.")
    private String content;
}