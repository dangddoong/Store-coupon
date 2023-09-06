package com.make.storecoupon.common.security.filter;

import com.make.storecoupon.common.auth.jwt.JwtUtil;
import com.make.storecoupon.common.auth.userDetails.service.UserDetailsServiceType;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
public class MartAuthFilter extends OncePerRequestFilter {

  private final JwtUtil jwtUtil;
  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    try {
      String martToken = jwtUtil.resolveToken(request, JwtUtil.MART_HEADER);
      if (martToken != null) {
        String loginId = jwtUtil.getLoginIdFromToken(martToken);
        Authentication authentication = jwtUtil.createAuthentication(loginId, UserDetailsServiceType.MART);
        SecurityContextHolder.getContext().setAuthentication(authentication);
      }
    } catch (Exception e) {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      return;
    }
    filterChain.doFilter(request, response);
  }
}