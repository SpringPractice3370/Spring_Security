package com.example.jwtoauth11.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MemberRegisterResponseDto {
    private Long id;
    private String email;
    private String authToken;

    @Builder
    public MemberRegisterResponseDto(Long id, String email, String authToken) {
        this.id = id;
        this.email = email;
        this.authToken = authToken;
    }

}
