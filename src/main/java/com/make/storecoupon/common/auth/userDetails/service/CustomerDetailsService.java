package com.make.storecoupon.common.auth.userDetails.service;

import com.make.storecoupon.common.auth.userDetails.entity.CustomerDetails;
import com.make.storecoupon.customer.entity.Customer;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomerDetailsService implements UserDetailsServiceAddGetType{

  @Override
  public UserDetailsServiceType getServiceType() {
    return UserDetailsServiceType.CUSTOMER;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//    Customer customer = customerRepository.findByLoginId(username)
//        .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
//    return new CustomerDetails(customer);
    return null;
  }
}
