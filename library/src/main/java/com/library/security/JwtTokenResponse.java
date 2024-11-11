package com.library.security;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JwtTokenResponse {
    private String accessToken;
    private String refreshToken;
}
