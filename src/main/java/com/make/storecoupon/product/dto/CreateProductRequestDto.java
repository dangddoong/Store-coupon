package com.make.storecoupon.product.dto;

import com.make.storecoupon.mart.entity.Mart;
import com.make.storecoupon.product.entity.Product;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreateProductRequestDto {

  @NotBlank
  private String productName;
  @Positive
  private Long productPrice;

  public Product toEntity(Mart mart){
    return Product.builder()
        .productName(productName)
        .productPrice(productPrice)
        .mart(mart)
        .build();
  }
}
