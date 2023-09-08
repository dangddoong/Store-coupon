package com.make.storecoupon.coupon.repository;

import com.make.storecoupon.coupon.entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponRepository extends JpaRepository<Coupon, Long> {

}
