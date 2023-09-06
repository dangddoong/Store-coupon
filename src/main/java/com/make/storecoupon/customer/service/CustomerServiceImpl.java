package com.make.storecoupon.customer.service;

import com.make.storecoupon.common.auth.jwt.JwtUtil;
import com.make.storecoupon.customer.dto.LoginRequestDto;
import com.make.storecoupon.customer.dto.RegisterRequestDto;
import com.make.storecoupon.customer.dto.TokenResponseDto;
import com.make.storecoupon.customer.entity.Customer;
import com.make.storecoupon.customer.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService{

  private final CustomerRepository customerRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtUtil jwtUtil;

  @Override
  @Transactional
  public Long register(RegisterRequestDto requestDto) {
    if(customerRepository.existsByLoginId(requestDto.getLoginId())){
      throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
    }
    String encodingPassword = passwordEncoder.encode(requestDto.getPassword());
    Customer customer = customerRepository.save(requestDto.toEntity(encodingPassword));
    return customer.getId();
  }

  @Override
  @Transactional
  public TokenResponseDto login(LoginRequestDto requestDto) {
    // 보안을 위해 아이디&비밀번호 관련 응답은 통일되게 설정하였음.
    Customer customer = customerRepository.findByLoginId(requestDto.getLoginId())
        .orElseThrow(() -> new IllegalArgumentException("아이디와 비밀번호를 확인해주세요"));
    if(!passwordEncoder.matches(requestDto.getPassword(), customer.getPassword())){
      throw new IllegalArgumentException("아이디와 비밀번호를 확인해주세요");
    }
    String accessToken = jwtUtil.createToken(customer.getLoginId(), JwtUtil.ACCESS_TOKEN_TIME);
    return new TokenResponseDto(accessToken);
  }
}
