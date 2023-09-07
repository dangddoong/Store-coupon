package com.make.storecoupon.common.auth.jwt;

import com.make.storecoupon.common.auth.userDetails.service.UserDetailsFactory;
import com.make.storecoupon.common.auth.userDetails.service.UserDetailsServiceType;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@RequiredArgsConstructor
public class JwtUtil {

  private final UserDetailsFactory userDetailsFactory;
  public static final String AUTHORIZATION_HEADER = "Authorization";
  public static final String MART_HEADER = "Mart";
  private static final String AUTHORIZATION_KEY = "auth"; // 사용자 권한 키값. 사용자 권한도 토큰안에 넣어주기 때문에 그때 사용하는 키값
  private static final String BEARER_PREFIX = "Bearer "; // Token 식별자
  public static final long ACCESS_TOKEN_TIME = 60 * 60 * 1000L; // 유효시간 1시간

  @Value("${jwt.secret.key}")
  private String secretKey;
  private Key key; // 토큰을 만들 때 넣어줄 키값
  private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

  @PostConstruct
  public void init() {
    byte[] bytes = Base64.getDecoder().decode(secretKey);
    key = Keys.hmacShaKeyFor(bytes); // key 객체에 넣어줄 때는 hmacShaKeyFor() 메서드를 사용해야한다.
  }

  public Authentication createAuthentication(String username, UserDetailsServiceType serviceType) {
    UserDetails userDetails = userDetailsFactory.getUserDetails(username, serviceType);
    return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
  }

  public  String resolveToken(HttpServletRequest request, String header) {
    String bearerToken = request.getHeader(header);
    if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
      return bearerToken.substring(7);
    }
    return null;
  }

  public String createToken(String username, Long tokenTime) {
    Date date = new Date();
    return BEARER_PREFIX +
        Jwts.builder()
            .setSubject(username)
            .claim(AUTHORIZATION_KEY, null)
            .setExpiration(new Date(date.getTime() + tokenTime))
            .setIssuedAt(date)
            .signWith(key, signatureAlgorithm)
            .compact();
  }

  public String getLoginIdFromToken(String token) {
    return Jwts.parserBuilder().setSigningKey(key)
        .build().parseClaimsJws(token).getBody()
        .getSubject();
  }

}
