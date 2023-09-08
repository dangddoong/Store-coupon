package com.make.storecoupon.coupon.entity;

import com.make.storecoupon.coupon.dto.CreateCouponRequestDto;
import com.make.storecoupon.mart.entity.Mart;
import com.make.storecoupon.product.entity.Product;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Coupon {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "mart_id")
  private Mart mart;
  @Column(nullable = false)
  private String name; // 쿠폰이름이나, 시리얼넘버로 사용되는 용도.
  @Column(nullable = false)
  @Enumerated(value = EnumType.STRING)
  private DiscountType discountType;
  @Column(nullable = false)
  private Long discountAmount; // discountType에 맞게 고정금액, 비율을 넣는 용도.
  @Column(nullable = false)
  @Enumerated(value = EnumType.STRING)
  private CoverageType coverageType; // 전체 상품 대상인지, 특정 상품 대상인지를 나타냄.
  @ElementCollection
  private List<Long> coverageProducts;

  @Builder
  private Coupon(Mart mart, String name, DiscountType discountType, Long discountAmount, CoverageType coverageType) {
    this.mart = mart;
    this.name = name;
    this.discountType = discountType;
    this.discountAmount = discountAmount;
    this.coverageType = coverageType;
    this.coverageProducts = new ArrayList<>();
  }

  public static Coupon createCouponIfValid(CreateCouponRequestDto requestDto, Mart mart){
    // 체크해야 될 조건(dicountType에 맞게 amount가 있는지, coveragetype에 맞게 productId가 왔는지(특정인데 null이면 예외던지기)
    Long discountAmount = requestDto.getDiscountAmount();
    DiscountType discountType = requestDto.getDiscountType();
    if (discountType == DiscountType.FIXED_AMOUNT && discountAmount < 1000L){
      throw new IllegalArgumentException("고정할인은 최소 1000원 이상부터 가능합니다.");
    }else if (discountType == DiscountType.PERCENTAGE && (discountAmount < 1L || discountAmount > 50L)) {
      throw new IllegalArgumentException("비율할인은 1~50% 사이만 가능합니다.");
    }
    if (requestDto.getCoverageType() == CoverageType.SPECIFIC_PRODUCT && requestDto.getCoverageProducts().isEmpty()){
      throw new IllegalArgumentException("특정상품 할인 쿠폰은 반드시 상품을 1개 이상 지정해야합니다.");
    }

    Coupon coupon =  Coupon.builder()
        .mart(mart)
        .name(requestDto.getName())
        .discountType(discountType)
        .discountAmount(discountAmount)
        .coverageType(requestDto.getCoverageType())
        .build();

    if(requestDto.getCoverageType() == CoverageType.SPECIFIC_PRODUCT){
      for(Long productId: requestDto.getCoverageProducts()){
        coupon.addCoverageProduct(productId);
      }
    }
    return coupon;
  }

  private void addCoverageProduct(Long productId) {
    this.coverageProducts.add(productId);
  }
}
