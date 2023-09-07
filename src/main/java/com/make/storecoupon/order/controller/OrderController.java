package com.make.storecoupon.order.controller;

import com.make.storecoupon.common.auth.userDetails.entity.CustomerDetails;
import com.make.storecoupon.order.dto.CreateOrderRequestDto;
import com.make.storecoupon.order.dto.CreateOrderResponseDto;
import com.make.storecoupon.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderController {
  private final OrderService orderService;

  @PostMapping("/marts/{martId}/orders")
  @PreAuthorize("hasRole('CUSTOMER')")
  public ResponseEntity<CreateOrderResponseDto> createOrder(@RequestBody CreateOrderRequestDto requestDto,
      @AuthenticationPrincipal CustomerDetails customerDetails, @PathVariable Long martId){
    CreateOrderResponseDto responseDto = orderService.createOrder(requestDto, customerDetails.getCustomer(), martId);
    return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
  }
}
