package com.make.storecoupon.customer.dto;

import com.make.storecoupon.customer.entity.Customer;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RegisterRequestDto {

  private String loginId;
  private String password;

  public Customer toEntity(String encodingPassword){
    return Customer.builder()
        .loginId(loginId)
        .password(encodingPassword)
        .build();
  }
}
