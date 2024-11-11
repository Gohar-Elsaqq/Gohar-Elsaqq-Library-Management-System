package com.library.repository;

import com.library.entity.TokenInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenInfoRepository extends JpaRepository<TokenInfo,Long> {

    TokenInfo findByRefreshToken(String refreshToken);
}
