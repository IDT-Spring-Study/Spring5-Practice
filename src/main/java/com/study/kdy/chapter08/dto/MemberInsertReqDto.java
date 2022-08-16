package com.study.kdy.chapter08.dto;

import com.study.kdy.chapter08.model.Member;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class MemberInsertReqDto {

    private final String email;
    private final String password;
    private final String name;
    private final LocalDateTime registerDateTime;

    public Member toEntity() {
        return new Member(email, password, name, registerDateTime);
    }

}
