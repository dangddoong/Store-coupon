package com.make.storecoupon.mart.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Mart {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(nullable = false, unique = true)
  private String loginId;
  @Column(nullable = false)
  private String password;
  @Column(nullable = false)
  @Enumerated(value = EnumType.STRING)
  private MartRole role;

  @Builder
  public Mart(String loginId, String password) {
    this.loginId = loginId;
    this.password = password;
    this.role = MartRole.MART;
  }
}
