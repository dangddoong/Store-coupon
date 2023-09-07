package com.make.storecoupon.order.service;

import com.make.storecoupon.order.entity.Order;
import com.make.storecoupon.order.entity.OrderProduct;
import com.make.storecoupon.order.repository.OrderProductRepository;
import com.make.storecoupon.product.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderProductService {
  private final OrderProductRepository orderProductRepository;

  @Transactional
  public void createOrderProduct(Order order, Product product, Long quantity) {
    OrderProduct orderProduct = OrderProduct.builder()
        .order(order)
        .product(product)
        .quantity(quantity)
        .build();
    orderProductRepository.save(orderProduct);
  }
}
