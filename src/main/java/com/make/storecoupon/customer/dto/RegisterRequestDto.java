package com.make.storecoupon.customer.dto;

import com.make.storecoupon.customer.entity.Customer;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RegisterRequestDto {

  private String loginId;
  @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$",
      message = "비밀번호는 최소 문자 1개, 숫자 1개를 포함한 8자리 이상으로 설정해주세요.")
  private String password;

  public Customer toEntity(String encodingPassword){
    return Customer.builder()
        .loginId(loginId)
        .password(encodingPassword)
        .build();
  }
}
