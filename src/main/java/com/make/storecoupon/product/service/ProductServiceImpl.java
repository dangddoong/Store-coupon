package com.make.storecoupon.product.service;

import com.make.storecoupon.mart.entity.Mart;
import com.make.storecoupon.product.dto.CreateProductRequestDto;
import com.make.storecoupon.product.dto.GetProductsResponseDto;
import com.make.storecoupon.product.dto.InquiryForMartDto;
import com.make.storecoupon.product.dto.UpdateProductRequestDto;
import com.make.storecoupon.product.entity.Product;
import com.make.storecoupon.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
  private final ProductRepository productRepository;

  @Override
  @Transactional
  public Long createProduct(CreateProductRequestDto requestDto, Mart mart) {
    Product product = productRepository.save(requestDto.toEntity(mart));
    return product.getId();
  }

//  @Override
//  @Transactional
//  public void updateProduct(UpdateProductRequestDto requestDto, Mart mart, Long productId) {
//
//  }
//
//  @Override
//  @Transactional
//  public void deleteProduct(Mart mart, Long productId) {
//
//  }
//
//  @Override
//  @Transactional(readOnly = true)
//  public GetProductsResponseDto getProducts(Long lastProductId) {
//    return null;
//  }
//
//  @Override
//  @Transactional(readOnly = true)
//  public InquiryForMartDto getProductsForMart(Mart mart, Long lastProductId) {
//    return null;
//  }
}
