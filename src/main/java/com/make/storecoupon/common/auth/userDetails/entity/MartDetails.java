package com.make.storecoupon.common.auth.userDetails.entity;

import com.make.storecoupon.mart.entity.Mart;
import java.util.ArrayList;
import java.util.Collection;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
@RequiredArgsConstructor
public class MartDetails implements UserDetails {

  private final Mart mart;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    String authority = mart.getRole().getAuthority();
    SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(authority);
    Collection<GrantedAuthority> authorities = new ArrayList<>();
    authorities.add(simpleGrantedAuthority);
    return authorities;
  }

  @Override
  public String getPassword() {
    return mart.getPassword();
  }
  @Override
  public String getUsername() {
    return mart.getLoginId();
  }

  @Override
  public boolean isAccountNonExpired() {return false;}
  @Override
  public boolean isAccountNonLocked() {return false;}
  @Override
  public boolean isCredentialsNonExpired() {return false;}
  @Override
  public boolean isEnabled() {return false;}

}
