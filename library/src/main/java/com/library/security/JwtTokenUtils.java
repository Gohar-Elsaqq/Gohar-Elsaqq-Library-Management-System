package com.library.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.nio.charset.StandardCharsets;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.util.Calendar;
import java.util.Date;

import static io.jsonwebtoken.Jwts.builder;

@Log4j2
@Component
public class JwtTokenUtils {
    private static String tokenSecret;
    private static Long accessTokenValidity;
    private static Long refreshTokenValidity;

    public JwtTokenUtils(@Value("${auth.secret}") String tokenSecret,
                         @Value("${auth.accessToken}") Long accessTokenValidity,
                         @Value("${auth.refresh.expiration}") Long refreshTokenValidity) {
        this.tokenSecret = tokenSecret;
        this.accessTokenValidity = accessTokenValidity;
        this.refreshTokenValidity = refreshTokenValidity;
    }

    public static String generateToken(String userName, String tokenId, boolean isRefresh, String role) {
        if (tokenSecret.length() < 64) {
            throw new IllegalArgumentException("The tokenSecret must be at least 64 characters for HS512.");
        }

        Key key = new SecretKeySpec(tokenSecret.getBytes(), SignatureAlgorithm.HS512.getJcaName());

        return Jwts.builder()
                .setId(tokenId)
                .setSubject(userName)
                .setIssuedAt(new Date())
                .setIssuer("app")
                .setExpiration(colcTokenExpiration(isRefresh))
                .claim("created", Calendar.getInstance().getTime())
                .claim("role", role)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    public static Date colcTokenExpiration(boolean isRefresh) {
        return new Date(System.currentTimeMillis() + (isRefresh ? refreshTokenValidity : accessTokenValidity) * 1000);
    }
    public boolean isTokenValid(String token,AppUserDatiles appUserDatiles){
        log.info("isTokenExpired"+isTokenExpired(token));
        String userName = getUsernameFromToken(token);
        log.info("Token User Name " + token);
        log.info("appUserDatiles.getUsername()"+appUserDatiles.getUsername());
        Boolean isUserNameEqual=userName.equalsIgnoreCase(appUserDatiles.getUsername());
        return (isUserNameEqual && !isTokenExpired(token));
    }

    public boolean isTokenExpired(String token){
        Date expiration = getClaims(token).getExpiration();
        return expiration.before(new Date());
    }
    //get username with token
    public String getUsernameFromToken(String token) {
        Claims claims = getClaims(token);
        return claims.getSubject();
    }

    public String getTokenIdWithToken(String token) {
        return getClaims(token).getId();
    }
    private Claims getClaims(String token) {
        Key key = Keys.hmacShaKeyFor(tokenSecret.getBytes());

        return Jwts.parser()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
//    public boolean validateToken(String token , HttpServletRequest httpServletRequest){
//        try {
//            Jwts.parser().setSigningKey(tokenSecret).build().parseClaimsJws(token).getBody();
//            return true;
//        }catch (SecurityException securityException){
//            log.info("Invalid JWT token");
//            return false;
//        }
//    }
    public boolean validateToken(String token) {
        try {
            Key key = new SecretKeySpec(tokenSecret.getBytes(StandardCharsets.UTF_8), SignatureAlgorithm.HS512.getJcaName());
            Jwts.parser()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);

            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT token");
            return false;
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT token");
            return false;
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT token");
            return false;
        } catch (IllegalArgumentException e) {
            log.info("JWT claims string is empty.");
            return false;
        }
    }


}
