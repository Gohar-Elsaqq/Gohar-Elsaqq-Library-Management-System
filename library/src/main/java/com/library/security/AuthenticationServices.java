package com.library.security;

import com.library.entity.AppUser;
import com.library.entity.TokenInfo;
import com.library.response.SuccessResponse;
import com.library.service.TokenInfoServices;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.util.List;
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
    @Autowired
    private JwtTokenUtils jwtTokenUtils;
    public SuccessResponse<JwtTokenResponse> login(String userName, String password) throws Exception {
        log.info("Attempting to authenticate user: " + userName);

        //Authentication using username password authentication code
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userName, password));

        log.info("Valid username and password");

        //Get user details after authentication

        AppUserDatiles appUserDatiles = (AppUserDatiles) authentication.getPrincipal();
        SecurityContextHolder.getContext().setAuthentication(authentication);
        List<GrantedAuthority> grantedAuthorities;
        String role = appUserDatiles.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .findFirst()
                .orElse("ROLE_USER");
        //Creation of token after validation
        TokenInfo tokenInfo = createLoginToken(userName, appUserDatiles.getId(),role);

        //Check token validity
        if (jwtTokenUtils.isTokenExpired(tokenInfo.getAccessToken())) {
            throw new Exception("Access token has expired");
        }

        //Return the result in the form of SuccessResponse
        return new SuccessResponse<>("Authentication successful",
                JwtTokenResponse.builder()
                        .accessToken(tokenInfo.getAccessToken())
                        .refreshToken(tokenInfo.getRefreshToken())
                        .build());
    }
    public TokenInfo createLoginToken(String userName, Long userId, String role) {
        String userAgent = httpRequest.getHeader(HttpHeaders.USER_AGENT);
        InetAddress ip = null;
        try {
            ip = InetAddress.getLocalHost();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String accessTokenId = UUID.randomUUID().toString();
        log.info("Access token create " + accessTokenId);
        String accessToken = JwtTokenUtils.generateToken(userName, accessTokenId, false, role);
        log.info("Access token created " + accessTokenId);
        String refreshTokenId = UUID.randomUUID().toString();
        log.info("Refresh token create " + refreshTokenId);
        String refreshToken = JwtTokenUtils.generateToken(userName, refreshTokenId, true, role);
        log.info("Refresh token created " + refreshTokenId);

        TokenInfo tokenInfo = new TokenInfo(accessToken, refreshToken);
        tokenInfo.setAppUser(new AppUser(userId));
        tokenInfo.setUserAgentText(userAgent);
        tokenInfo.setLocalIpAddress(ip.getHostAddress());
        tokenInfo.setRemoteIpAddress(httpRequest.getRemoteAddr());
        return tokenInfoServices.saveTokenInfo(tokenInfo);
    }

}
