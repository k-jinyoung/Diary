package com.codehows.diary.config.jwt;
//토큰을 생성하고 올바른 토큰인지 유효성을 검사하고, 토큰에서 필요한 정보를 가져오는 TokenProvider 작성

import com.codehows.diary.domain.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Collections;
import java.util.Date;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class TokenProvider {

    private final JwtProperties jwtProperties;

    public String generateToken(User user, Duration expiredAt) {
        Date now = new Date();
        return makeToken(new Date(now.getTime() + expiredAt.toMillis()), user);
    }

    //makeToken() 메소드 생성
    private String makeToken(Date expiry, User user){
        Date now = new Date();

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)       //헤더 type : JWT
                .setIssuer(jwtProperties.getIssuer())
                .setIssuedAt(now)                                   //내용 iat : 현재시간
                .setExpiration(expiry)                              //내용 exp : expiry 멤버 변수값
                .setSubject(user.getEmail())                        //내용 sub : 유저의 이메일
                .claim("id", user.getId())                       //클레임 id : 유저 ID
                //서명 : 비밀값과 함께 해시값을 HS256 방식으로 암호화
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecretKey())
                .compact();
    }

    //JWT 토큰 유효성 검증 메소드
    public boolean validToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(jwtProperties.getSecretKey())            //비밀값으로 암호화
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {                 //복호화 과정에서 에러가 나면 유효하지 않은 토큰이다.
            return false;
        }
    }

    //토큰 기반으로 인증 정보를 가져오는 메소드
    public Authentication getAuthentication(String token) {
        Claims claims = getClaims(token);
        Set<SimpleGrantedAuthority> authorities = Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));

        return new UsernamePasswordAuthenticationToken(new org.springframework.security.core.userdetails.User(claims.getSubject(), "", authorities), token, authorities);
    }

    //토큰 기반으로 유저 Id를 가져오는 메소드
    public Long getUserId(String token) {
        Claims claims = getClaims(token);
        return claims.get("id", Long.class);
    }

    private Claims getClaims(String token) {
        return Jwts.parser()    //클레임 조회
                .setSigningKey(jwtProperties.getSecretKey())
                .parseClaimsJws(token)
                .getBody();
    }
}
