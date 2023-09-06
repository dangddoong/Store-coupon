package com.make.storecoupon.customer.controller;

import com.make.storecoupon.customer.dto.LoginRequestDto;
import com.make.storecoupon.customer.dto.RegisterRequestDto;
import com.make.storecoupon.customer.dto.TokenResponseDto;
import com.make.storecoupon.customer.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CustomerController {

  private final CustomerService customerService;

  @PostMapping("/customer/register")
  public ResponseEntity<Long> register(@Valid @RequestBody RegisterRequestDto requestDto){
    Long customerId = customerService.register(requestDto);
    return ResponseEntity.status(HttpStatus.CREATED).body(customerId);
  }

  @PostMapping("/customer/login")
  public TokenResponseDto login(@RequestBody LoginRequestDto requestDto){
    return customerService.login(requestDto);
  }
}
