package com.make.storecoupon.customer.service;

import com.make.storecoupon.customer.dto.LoginRequestDto;
import com.make.storecoupon.customer.dto.RegisterRequestDto;
import com.make.storecoupon.customer.dto.TokenResponseDto;

public interface CustomerService {
  Long register(RegisterRequestDto requestDto);
  TokenResponseDto login(LoginRequestDto requestDto);
}
