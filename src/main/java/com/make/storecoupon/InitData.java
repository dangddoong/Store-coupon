package com.make.storecoupon;

import com.make.storecoupon.mart.entity.Mart;
import com.make.storecoupon.mart.service.MartService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InitData implements ApplicationRunner {
  private final MartService martService;
  @Override
  public void run(ApplicationArguments args) throws Exception {
    martService.register("testMart", "test1234");
  }
}
