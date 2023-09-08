package com.make.storecoupon.coupon.repository;

import com.make.storecoupon.coupon.entity.IssuedCoupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IssuedCouponRepository extends JpaRepository<IssuedCoupon, Long> {

}
