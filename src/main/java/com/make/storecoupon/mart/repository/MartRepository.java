package com.make.storecoupon.mart.repository;

import com.make.storecoupon.mart.entity.Mart;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MartRepository extends JpaRepository<Mart, Long> {
  Optional<Mart> findByLoginId(String loginId);
  boolean existsByLoginId(String loginId);
}
