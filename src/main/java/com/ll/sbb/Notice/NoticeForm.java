package com.ll.sbb.Notice;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NoticeForm {

    @NotEmpty(message = "제목 입력은 필수항목입니다.")
    @Size(max = 200)
    private String subject;

    @NotEmpty(message = "내용 입력은 필수항목입니다.")
    private String content;
}
