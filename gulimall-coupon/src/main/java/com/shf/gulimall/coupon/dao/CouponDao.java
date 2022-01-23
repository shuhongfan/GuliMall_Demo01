package com.shf.gulimall.coupon.dao;

import com.shf.gulimall.coupon.entity.CouponEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 优惠券信息
 * 
 * @author shuhongfan
 * @email shuhongfan@live.com
 * @date 2022-01-20 15:27:54
 */
@Mapper
public interface CouponDao extends BaseMapper<CouponEntity> {
	
}
