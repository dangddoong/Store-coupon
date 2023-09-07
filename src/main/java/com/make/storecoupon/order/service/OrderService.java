package com.make.storecoupon.order.service;

import com.make.storecoupon.customer.entity.Customer;
import com.make.storecoupon.order.dto.CreateOrderRequestDto;
import com.make.storecoupon.order.dto.CreateOrderResponseDto;

public interface OrderService {
  CreateOrderResponseDto createOrder(CreateOrderRequestDto requestDto, Customer customer, Long martId);
}
