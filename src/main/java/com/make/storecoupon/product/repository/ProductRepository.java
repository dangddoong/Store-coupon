package com.make.storecoupon.product.repository;

import com.make.storecoupon.product.entity.Product;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
  List<Product> findAllByIdIn(List<Long> productIds);
}
