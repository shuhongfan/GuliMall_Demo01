package com.shf.gulimall.order.dao;

import com.shf.gulimall.order.entity.OrderEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单
 * 
 * @author shuhongfan
 * @email shuhongfan@live.com
 * @date 2022-01-20 15:45:14
 */
@Mapper
public interface OrderDao extends BaseMapper<OrderEntity> {
	
}
