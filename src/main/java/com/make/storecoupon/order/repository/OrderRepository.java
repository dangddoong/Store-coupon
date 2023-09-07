package com.make.storecoupon.order.repository;

import com.make.storecoupon.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
