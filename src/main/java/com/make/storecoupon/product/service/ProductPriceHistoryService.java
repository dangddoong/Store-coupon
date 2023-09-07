package com.make.storecoupon.product.service;

import com.make.storecoupon.product.dto.ProductPriceHistoryResponseDto;
import com.make.storecoupon.product.entity.Product;
import com.make.storecoupon.product.entity.ProductPriceHistory;
import com.make.storecoupon.product.repository.ProductPriceHistoryRepository;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductPriceHistoryService {
  private final ProductPriceHistoryRepository productPriceHistoryRepository;

  @Transactional
  public void createPriceHistory(Product product) {
    ProductPriceHistory productPriceHistory = ProductPriceHistory.builder()
        .priceUpdateAt(LocalDateTime.now())
        .updatedPrice(product.getProductPrice())
        .product(product)
        .build();
    productPriceHistoryRepository.save(productPriceHistory);
  }

  @Transactional(readOnly = true)
  public ProductPriceHistoryResponseDto getProductPriceAtTime(LocalDateTime dateTime, Long productId) {
    ProductPriceHistory productPriceHistory = productPriceHistoryRepository.getProductPriceAtTime(dateTime, productId)
        .orElseThrow(() -> new IllegalArgumentException("해당시점의 가격이 존재하지 않습니다."));
     return new ProductPriceHistoryResponseDto(productId, dateTime, productPriceHistory.getUpdatedPrice());
  }
}
