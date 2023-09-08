package com.make.storecoupon.coupon.service;


import com.make.storecoupon.coupon.dto.CreateCouponRequestDto;
import com.make.storecoupon.coupon.entity.Coupon;
import com.make.storecoupon.coupon.entity.IssuedCoupon;
import com.make.storecoupon.coupon.repository.CouponRepository;
import com.make.storecoupon.coupon.repository.IssuedCouponRepository;
import com.make.storecoupon.customer.entity.Customer;
import com.make.storecoupon.mart.entity.Mart;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CouponServiceImpl implements CouponService{
  private final CouponRepository couponRepository;
  private final IssuedCouponRepository issuedCouponRepository;

  @Override
  @Transactional
  public Long createCoupon(CreateCouponRequestDto requestDto, Mart mart) {
    Coupon coupon = Coupon.createCouponIfValid(requestDto, mart);
    couponRepository.save(coupon);
    return coupon.getId();
  }

  @Override
  @Transactional
  public Long issueCoupon(Customer customer, Long couponId) {
    Coupon coupon = couponRepository.findById(couponId)
        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 쿠폰입니다."));
    IssuedCoupon issuedCoupon = new IssuedCoupon(coupon, customer.getId());
    issuedCouponRepository.save(issuedCoupon);
    return issuedCoupon.getId();
  }
}
