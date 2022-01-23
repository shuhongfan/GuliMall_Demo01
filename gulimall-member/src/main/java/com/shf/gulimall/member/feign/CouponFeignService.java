package com.shf.gulimall.member.feign;

import com.shf.common.utils.R;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 这是一个声明式远程调用
 * 想要远程调用别的服务
 * 1.引入open-feign
 * 2.编写一个接口，告诉springcloud这个接口需要调用远程服务
 * 3.声明接口的每一个方法都是调用哪个远程服务的哪个请求
 * 4.开启远程调用功能
 */

@FeignClient("gulimall-coupon")
public interface CouponFeignService {
    @RequestMapping("/coupon/coupon/member/list")
    public R membercoupons();
}
