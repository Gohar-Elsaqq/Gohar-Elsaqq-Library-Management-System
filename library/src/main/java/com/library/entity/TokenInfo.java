package com.library.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "token_info")
public class TokenInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @Column(length = 10000)
    private String accessToken;
    @NotBlank
    @Column(length = 10000)
    private String refreshToken;
    private String userAgentText;
    private String localIpAddress;
    private String remoteIpAddress;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID",referencedColumnName = "ID")
    private AppUser appUser;

    public TokenInfo(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
