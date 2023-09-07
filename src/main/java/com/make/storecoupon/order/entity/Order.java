package com.make.storecoupon.order.entity;

import com.make.storecoupon.customer.entity.Customer;
import com.make.storecoupon.mart.entity.Mart;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "orders")
public class Order {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "customer_id")
  private Customer customer;
  private Long martId;
  @Column(nullable = false)
  private Long deliveryCharge;
  @Column(nullable = false)
  private Long totalPaymentAmount;

  @Builder
  public Order(Customer customer, Long martId, Long deliveryCharge, Long totalPaymentAmount) {
    this.customer = customer;
    this.martId = martId;
    this.deliveryCharge = deliveryCharge;
    this.totalPaymentAmount = totalPaymentAmount;
  }
}
