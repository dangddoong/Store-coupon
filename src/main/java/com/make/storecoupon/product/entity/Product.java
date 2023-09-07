package com.make.storecoupon.product.entity;

import com.make.storecoupon.mart.entity.Mart;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(indexes = @Index(name = "mart_index", columnList = "mart_id"))
public class Product {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "mart_id")
  private Mart mart;
  @Column(nullable = false)
  private String productName;
  @Column(nullable = false)
  private Long productPrice;
  @Column(nullable = false)
  @Enumerated(value = EnumType.STRING)
  private SalesStatus salesStatus;

  @Builder
  private Product(Mart mart, String productName, Long productPrice) {
    this.mart = mart;
    this.productName = productName;
    this.productPrice = productPrice;
    this.salesStatus = SalesStatus.ON_SALE;
  }

  public void validateOwner(Long martId){
    if(this.id.equals(martId)) throw new IllegalArgumentException("해당제품에 접근권한이 없습니다.");
  }
  public void updateProductName(String productName){
    this.productName = productName;
  }
  public void updateProductPrice(Long productPrice){
    this.productPrice = productPrice;
  }
}
