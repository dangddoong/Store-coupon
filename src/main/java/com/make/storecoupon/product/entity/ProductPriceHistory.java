package com.make.storecoupon.product.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(indexes = @Index(name = "product_index", columnList = "product_id"))
public class ProductPriceHistory {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "product_id")
  private Product product;
  @Column(nullable = false)
  private LocalDateTime priceUpdateAt;
  @Column(nullable = false)
  private Long updatedPrice;
  @Builder
  public ProductPriceHistory(Product product, LocalDateTime priceUpdateAt, Long updatedPrice) {
    this.product = product;
    this.priceUpdateAt = priceUpdateAt;
    this.updatedPrice = updatedPrice;
  }
}
