package com.make.storecoupon.mart.service;

import com.make.storecoupon.common.auth.jwt.JwtUtil;
import com.make.storecoupon.mart.dto.LoginRequestDto;
import com.make.storecoupon.mart.dto.TokenResponseDto;
import com.make.storecoupon.mart.entity.Mart;
import com.make.storecoupon.mart.repository.MartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MartServiceImpl implements MartService {

  private final MartRepository martRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtUtil jwtUtil;

  @Override
  @Transactional
  public Long register(String loginId, String password) {
    if(martRepository.existsByLoginId(loginId)){
      throw new IllegalArgumentException("중복된 마트가 존재합니다.");
    }
    String encodingPassword = passwordEncoder.encode(password);
    Mart mart = martRepository.save(new Mart(loginId, encodingPassword));
    return mart.getId();
  }
  @Override
  @Transactional(readOnly = true)
  public TokenResponseDto login(LoginRequestDto requestDto) {
    Mart mart = martRepository.findByLoginId(requestDto.getLoginId())
        .orElseThrow(() -> new IllegalArgumentException("아이디와 비밀번호를 확인해주세요"));
    if (!passwordEncoder.matches(requestDto.getPassword(), mart.getPassword())) {
      throw new IllegalArgumentException("아이디와 비밀번호를 확인해주세요");
    }
    String accessToken = jwtUtil.createToken(mart.getLoginId(), JwtUtil.ACCESS_TOKEN_TIME);
    return new TokenResponseDto(accessToken);
  }
}
