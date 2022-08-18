package com.example.jwtoauth11.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MemberRegisterResponseDto {
    private Long id;
    private String email;

    @Builder
    public MemberRegisterResponseDto(Long id, String email) {
        this.id = id;
        this.email = email;
    }

}
