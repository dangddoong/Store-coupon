package com.make.storecoupon.mart.service;

import com.make.storecoupon.mart.dto.LoginRequestDto;
import com.make.storecoupon.mart.dto.TokenResponseDto;
import org.springframework.transaction.annotation.Transactional;

public interface MartService {
  Long register(String loginId, String password);
  TokenResponseDto login(LoginRequestDto requestDto);
}
