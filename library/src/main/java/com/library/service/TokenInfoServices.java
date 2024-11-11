package com.library.service;

import com.library.entity.TokenInfo;
import com.library.repository.TokenInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TokenInfoServices {
    @Autowired
    private TokenInfoRepository tokenInfoRepository;

    public TokenInfo findByRefreshToken(String refreshToken){
        return tokenInfoRepository.findByRefreshToken(refreshToken);
    }
    public TokenInfo saveTokenInfo(TokenInfo tokenInfo){
      return   tokenInfoRepository.save(tokenInfo);
    }

}
