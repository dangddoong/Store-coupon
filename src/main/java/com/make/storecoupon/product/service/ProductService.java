package com.make.storecoupon.product.service;

import com.make.storecoupon.mart.entity.Mart;
import com.make.storecoupon.product.dto.CreateProductRequestDto;
import com.make.storecoupon.product.dto.GetProductsResponseDto;
import com.make.storecoupon.product.dto.InquiryForMartDto;
import com.make.storecoupon.product.dto.UpdateProductRequestDto;
import com.make.storecoupon.product.entity.Product;
import java.util.List;

public interface ProductService {
  Long createProduct(CreateProductRequestDto requestDto, Mart mart);
  void updateProduct(UpdateProductRequestDto requestDto, Mart mart, Long productId);
  void deleteProduct(Mart mart, Long productId);
  List<Product> getProductsByIds(List<Long> productIds);
//  GetProductsResponseDto getProducts(Long lastProductId);
//  InquiryForMartDto getProductsForMart(Mart mart, Long lastProductId);
}
