package com.make.storecoupon.common.auth.userDetails.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserDetailsServiceAddGetType extends UserDetailsService {
  UserDetailsServiceType getServiceType();
}
