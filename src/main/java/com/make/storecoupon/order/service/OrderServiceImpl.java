package com.make.storecoupon.order.service;

import com.make.storecoupon.customer.entity.Customer;
import com.make.storecoupon.order.dto.CreateOrderRequestDto;
import com.make.storecoupon.order.dto.CreateOrderResponseDto;
import com.make.storecoupon.order.dto.OrderDetailsDto;
import com.make.storecoupon.order.entity.Order;
import com.make.storecoupon.order.repository.OrderRepository;
import com.make.storecoupon.product.entity.Product;
import com.make.storecoupon.product.service.ProductService;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{
  private final OrderRepository orderRepository;
  private final OrderProductService orderProductService;
  private final ProductService productService;

  @Override
  @Transactional
  public CreateOrderResponseDto createOrder(CreateOrderRequestDto requestDto, Customer customer, Long martId) {
    List<Product> products = productService.getProductsByIds(requestDto.getProductIds());
    validateProductsIsOrderedSingleMart(products);  // 사업 특성상 '하나의 마트'에서 주문되어야한다는 규칙을 두었습니다.
    Map<Long, Product> productMap = products.stream()
        .collect(Collectors.toMap(Product::getId, Function.identity()));
    Order order = Order.builder()
        .customer(customer)
        .martId(martId)
        .totalPaymentAmount(
            calculateTotalPaymentAmount(requestDto.getOrderDetailsDtos(), productMap, requestDto.getDeliveryCharge()))
        .deliveryCharge(requestDto.getDeliveryCharge()).build();
    orderRepository.save(order);
    createOrderProducts(requestDto.getOrderDetailsDtos(), productMap, order);
    //
    // 쿠폰적용시 실 결제금액이 마이너스라면 예외던지기.  쿠폰은 하나만 보낼 수 있게 하기.(포스트맨으로 두개 보내봐서 어떻게되나 테스트하기)
    return new CreateOrderResponseDto(order);
  }


  private void createOrderProducts(List<OrderDetailsDto> orderDetailsDtos, Map<Long, Product> productMap, Order order) {
    for (OrderDetailsDto detailsDto: orderDetailsDtos){
      Product product = productMap.get(detailsDto.getProductId());
      orderProductService.createOrderProduct(order, product, detailsDto.getQuantity());
    }
  }

  private Long calculateTotalPaymentAmount(List<OrderDetailsDto> orderDetailsDtos, Map<Long, Product> productMap,
      Long deliveryCharge) {
    Long totalPaymentAmount = deliveryCharge; // 배달료에 상세주문별 금액을 더해서 총 결제금액을 리턴
    for (OrderDetailsDto dto: orderDetailsDtos){
      Long price = productMap.get(dto.getProductId()).getProductPrice();
      totalPaymentAmount += dto.getQuantity() * price;
    }
    return totalPaymentAmount;
  }

  private void validateProductsIsOrderedSingleMart(List<Product> products) {
    Long firstProductsMartId = products.get(0).getMart().getId();
    for (Product product: products){
      if (!firstProductsMartId.equals(product.getMart().getId())) {
        throw new IllegalArgumentException("주문별로 동일한 마트의 상품만 주문 가능합니다.");
      }
    }
  }
}
