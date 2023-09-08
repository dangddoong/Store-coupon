package com.make.storecoupon.order.service;

import com.make.storecoupon.coupon.entity.Coupon;
import com.make.storecoupon.coupon.entity.CoverageType;
import com.make.storecoupon.coupon.entity.DiscountType;
import com.make.storecoupon.coupon.entity.IssuedCoupon;
import com.make.storecoupon.coupon.repository.IssuedCouponRepository;
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
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{
  private final OrderRepository orderRepository;
  private final OrderProductService orderProductService;
  private final ProductService productService;
  private final IssuedCouponRepository issuedCouponRepository;

  @Override
  @Transactional
  public CreateOrderResponseDto createOrder(CreateOrderRequestDto requestDto, Customer customer, Long martId) {
    List<Product> products = productService.getProductsByIds(requestDto.getProductIds());
    validateProductsIsOrderedSingleMart(products);  // 사업 특성상 '하나의 마트'에서 주문되어야한다는 규칙을 두었습니다.
    Map<Long, Product> productMap = products.stream()
        .collect(Collectors.toMap(Product::getId, Function.identity()));
    IssuedCoupon issuedCoupon = getIssuedCouponIfExist(requestDto);
    if (issuedCoupon != null && !issuedCoupon.getCoupon().getMart().getId().equals(martId)){
      throw new IllegalArgumentException("쿠폰은 발급한 마트에서만 사용할 수 있습니다.");
    }
    Order order = createOrderWithCalculatePaymentAmount(requestDto, customer, martId, productMap, issuedCoupon);
    orderRepository.save(order);
    createOrderProducts(requestDto.getOrderDetailsDtos(), productMap, order);
    return new CreateOrderResponseDto(order);
  }


  private Order createOrderWithCalculatePaymentAmount(CreateOrderRequestDto requestDto, Customer customer, Long martId,
      Map<Long, Product> productMap, IssuedCoupon issuedCoupon) {
    Long totalPaymentAmount =
        totalPaymentAmountCalculate(requestDto.getOrderDetailsDtos(), productMap, requestDto.getDeliveryCharge());
    Long actualPaymentAmount =
        actualPaymentAmountCalculate(requestDto, productMap, totalPaymentAmount, issuedCoupon);
    Order order = Order.builder()
        .customer(customer)
        .martId(martId)
        .totalPaymentAmount(totalPaymentAmount)
        .actualPaymentAmount(actualPaymentAmount)
        .deliveryCharge(requestDto.getDeliveryCharge())
        .issuedCoupon(issuedCoupon)
        .build();
    return order;
  }


  private Long actualPaymentAmountCalculate(CreateOrderRequestDto requestDto, Map<Long, Product> productMap,
      Long totalPaymentAmount, IssuedCoupon issuedCoupon) {
    // 쿠폰이 존재하지 않으면 총 결제금액을 반환
    if (issuedCoupon == null) return totalPaymentAmount;
    Coupon coupon = issuedCoupon.getCoupon();
    // 만약 쿠폰의 적용범위가 '특정상품'이라면, 주문이 '특정상품'을 포함하고 있는지 검사.
    if (coupon.getCoverageType() == CoverageType.SPECIFIC_PRODUCT){
      boolean isCoverageProductInOrder = false;
      for(Long productId : productMap.keySet()){
        if(coupon.getCoverageProducts().contains(productId)){
          isCoverageProductInOrder = true;
        }
      }
      if (!isCoverageProductInOrder) throw new IllegalArgumentException("해당쿠폰을 적용할 수 있는 상품이 없습니다.");
    }
    // 쿠폰의 할인타입이 고정금액이라면, 고정금액만큼 할인한 값을 반환 (음수체크 포함)
    if (coupon.getDiscountType() == DiscountType.FIXED_AMOUNT){
      Long actualPaymentAmount = totalPaymentAmount - coupon.getDiscountAmount();
      if (actualPaymentAmount <= 0L) throw new IllegalArgumentException("할인금액이 결제금액을 초과할 수 없습니다.");
      return actualPaymentAmount;
    }

    // 쿠폰의 할인타입이 비율이라면, 적용타입에 따라 실 결제금액을 계산하여 반환 (비율할인은 음수가 될 수 없으므로 체크하지 않음)
    Long deliveryCharge = requestDto.getDeliveryCharge();
    Long discountPercent = (100L - coupon.getDiscountAmount()) / 100L;
    switch (coupon.getCoverageType()) {
      case ALL_PRODUCTS -> {
        return (((totalPaymentAmount - deliveryCharge) * discountPercent) ) + deliveryCharge;
      }

      case SPECIFIC_PRODUCT -> {
        Long actualPaymentAmount = deliveryCharge; // 배달료에 상세주문별 금액을 더해서 총 결제금액을 리턴
        for (OrderDetailsDto dto: requestDto.getOrderDetailsDtos()){
          Long price = productMap.get(dto.getProductId()).getProductPrice();
          if(coupon.getCoverageProducts().contains(dto.getProductId())) {
              actualPaymentAmount += (dto.getQuantity() * price) * discountPercent;
          }else{
            actualPaymentAmount += dto.getQuantity() * price;
          }
        }
        return actualPaymentAmount;
      }
    }
    return totalPaymentAmount;
  }

  private Long totalPaymentAmountCalculate(List<OrderDetailsDto> orderDetailsDtos, Map<Long, Product> productMap,
      Long deliveryCharge) {
    Long totalPaymentAmount = deliveryCharge; // 배달료에 상세주문별 금액을 더해서 총 결제금액을 리턴
    for (OrderDetailsDto dto: orderDetailsDtos){
      Long price = productMap.get(dto.getProductId()).getProductPrice();
      totalPaymentAmount += dto.getQuantity() * price;
    }
    return totalPaymentAmount;
  }
  private void createOrderProducts(List<OrderDetailsDto> orderDetailsDtos, Map<Long, Product> productMap, Order order) {
    for (OrderDetailsDto detailsDto: orderDetailsDtos){
      Product product = productMap.get(detailsDto.getProductId());
      orderProductService.createOrderProduct(order, product, detailsDto.getQuantity());
    }
  }

  private void validateProductsIsOrderedSingleMart(List<Product> products) {
    Long firstProductsMartId = products.get(0).getMart().getId();
    for (Product product: products){
      if (!firstProductsMartId.equals(product.getMart().getId())) {
        throw new IllegalArgumentException("주문별로 동일한 마트의 상품만 주문 가능합니다.");
      }
    }
  }
  private IssuedCoupon getIssuedCouponIfExist(CreateOrderRequestDto requestDto) {
    if (requestDto.getIssuedCouponId() != null){
      return issuedCouponRepository.findById(requestDto.getIssuedCouponId())
          .orElseThrow(()->new IllegalArgumentException("존재하지 않는 쿠폰입니다."));
    }
    return null;
  }
}
