package com.make.storecoupon.customer.repository;

import com.make.storecoupon.customer.entity.Customer;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
  Optional<Customer> findByLoginId(String loginId);
  boolean existsByLoginId(String loginId);
}
