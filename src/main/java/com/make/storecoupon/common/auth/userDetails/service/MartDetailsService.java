package com.make.storecoupon.common.auth.userDetails.service;

import com.make.storecoupon.common.auth.userDetails.entity.MartDetails;
import com.make.storecoupon.mart.entity.Mart;
import com.make.storecoupon.mart.repository.MartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MartDetailsService implements UserDetailsServiceAddGetType{
  private final MartRepository martRepository;

  @Override
  public UserDetailsServiceType getServiceType() {
    return UserDetailsServiceType.MART;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Mart mart = martRepository.findByLoginId(username)
        .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
    return new MartDetails(mart);
  }
}
