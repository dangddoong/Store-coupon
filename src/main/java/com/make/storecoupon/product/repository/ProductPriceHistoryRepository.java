package com.make.storecoupon.product.repository;

import com.make.storecoupon.product.entity.ProductPriceHistory;
import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductPriceHistoryRepository extends JpaRepository<ProductPriceHistory, Long> {
  @Query(value = "select pph from ProductPriceHistory pph "
          + "where pph.product.id = :productId and pph.priceUpdateAt <= :dateTime "
          + "order by pph.priceUpdateAt desc limit 1")
  Optional<ProductPriceHistory> getProductPriceAtTime(@Param("dateTime") LocalDateTime dateTime, @Param("productId")Long productId);
}
