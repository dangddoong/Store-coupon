package com.make.storecoupon.product.controller;

import com.make.storecoupon.common.auth.userDetails.entity.MartDetails;
import com.make.storecoupon.product.dto.CreateProductRequestDto;
import com.make.storecoupon.product.dto.GetProductsResponseDto;
import com.make.storecoupon.product.dto.InquiryForMartDto;
import com.make.storecoupon.product.dto.UpdateProductRequestDto;
import com.make.storecoupon.product.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProductController {

  private final ProductService productService;

  @PreAuthorize("hasRole('MART')")
  @PostMapping("/products")
  public ResponseEntity<Long> createProduct(@AuthenticationPrincipal MartDetails martDetails,
      @Valid @RequestBody CreateProductRequestDto requestDto){
    Long productId = productService.createProduct(requestDto, martDetails.getMart());
    return ResponseEntity.status(HttpStatus.CREATED).body(productId);
  }

  @PatchMapping("/products/{productId}")
  public ResponseEntity<String> updateProduct(@AuthenticationPrincipal MartDetails martDetails,
      @PathVariable Long productId, @Valid @RequestBody UpdateProductRequestDto requestDto){
    productService.updateProduct(requestDto, martDetails.getMart(), productId);
    return new ResponseEntity<>("제품 업데이트 성공", HttpStatus.OK);
  }
//
//  @DeleteMapping("/products/{productId}")
//  public ResponseEntity<String> deleteProduct(@AuthenticationPrincipal MartDetails martDetails,
//      @PathVariable Long productId){
//    productService.deleteProduct(martDetails.getMart(), productId);
//    return new ResponseEntity<>("제품 삭제 성공", HttpStatus.OK);
//  }
//
//  @GetMapping("/products")
//  public GetProductsResponseDto getProducts(@RequestParam Long lastProductId){
//    return productService.getProducts(lastProductId);
//  }
//
//  @GetMapping("/marts/products")
//  public InquiryForMartDto getProductsForMart(@AuthenticationPrincipal MartDetails martDetails,
//      @RequestParam Long lastProductId){
//    return productService.getProductsForMart(martDetails.getMart(), lastProductId);
//  }
}
