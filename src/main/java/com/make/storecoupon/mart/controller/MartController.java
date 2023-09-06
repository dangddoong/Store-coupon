package com.make.storecoupon.mart.controller;

import com.make.storecoupon.mart.dto.LoginRequestDto;
import com.make.storecoupon.mart.dto.TokenResponseDto;
import com.make.storecoupon.mart.service.MartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MartController {
  private final MartService martService;

  @PostMapping("/mart/login")
  public TokenResponseDto login(@RequestBody LoginRequestDto requestDto){
    return martService.login(requestDto);
  }

}
