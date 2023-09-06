package com.make.storecoupon.common.security.filter;

import com.make.storecoupon.common.auth.jwt.JwtUtil;
import com.make.storecoupon.common.auth.userDetails.service.UserDetailsServiceType;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
public class CustomerAuthFilter extends OncePerRequestFilter {

  private final JwtUtil jwtUtil;
  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    try {
      String customerToken = jwtUtil.resolveToken(request, JwtUtil.AUTHORIZATION_HEADER);
      if (customerToken != null) {
        String loginId = jwtUtil.getLoginIdFromToken(customerToken);
        Authentication authentication = jwtUtil.createAuthentication(loginId, UserDetailsServiceType.CUSTOMER);
        SecurityContextHolder.getContext().setAuthentication(authentication);
      }
    } catch (Exception e) {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      return;
    }
    filterChain.doFilter(request, response);
  }
}
