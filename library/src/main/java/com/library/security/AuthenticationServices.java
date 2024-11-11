package com.library.security;

import com.library.entity.AppUser;
import com.library.entity.TokenInfo;
import com.library.service.TokenInfoServices;
import jakarta.persistence.Access;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.util.UUID;

@Service
@Log4j2
public class AuthenticationServices {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private HttpServletRequest httpRequest;
    @Autowired
    private TokenInfoServices tokenInfoServices;
    public JwtTokenResponse login(String userName,String password){
        log.info("Attempting to authenticate user: " + userName);
        Authentication authentication=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName,password));
        log.info("valid username and password");
        AppUserDatiles appUserDatiles = (AppUserDatiles) authentication.getPrincipal();
        SecurityContextHolder.getContext().setAuthentication(authentication);

        TokenInfo tokenInfo= createLoginToken(userName,appUserDatiles.getId());


        return JwtTokenResponse.builder()
                .accessToken(tokenInfo.getAccessToken())
                .refreshToken(tokenInfo.getRefreshToken())
                .build();
    }
    public TokenInfo createLoginToken(String userName,Long userId ){
        String userAgent = httpRequest.getHeader(HttpHeaders.USER_AGENT);
        InetAddress ip=null;
        try {
            ip = InetAddress.getLocalHost();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String accessTokenId = UUID.randomUUID().toString();
        log.info("Access token create " + accessTokenId);
        String accessTken =JwtTokenUtils.generateToken(userName,accessTokenId,false);
        log.info("Access token create " + accessTokenId);
        String refreshTokenId = UUID.randomUUID().toString();
        log.info("Refresh token create " + refreshTokenId);
        String refreshToken = JwtTokenUtils.generateToken(userName, refreshTokenId,true);
        log.info("Refresh token create " + refreshTokenId);
        TokenInfo tokenInfo = new TokenInfo(accessTken,refreshToken);
        tokenInfo.setAppUser(new AppUser(userId));
        tokenInfo.setUserAgentText(userAgent);
        tokenInfo.setLocalIpAddress(ip.getHostAddress());
        tokenInfo.setRemoteIpAddress(httpRequest.getRemoteAddr());
        return tokenInfoServices.saveTokenInfo(tokenInfo);
    }
}
