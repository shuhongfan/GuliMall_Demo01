package com.shf.gulimall.product;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.shf.gulimall.product.entity.BrandEntity;
import com.shf.gulimall.product.service.BrandService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class GulimallProductApplicationTests {

    @Autowired
    private BrandService brandService;

    @Test
    void contextLoads() {
//        插入
//        BrandEntity brandEntity = new BrandEntity();
//        brandEntity.setName("华为");
//        brandService.save(brandEntity);

//        更新
//        BrandEntity brandEntity = new BrandEntity();
//        brandEntity.setBrandId(1L);
//        brandEntity.setDescript("华为");
//        brandService.updateById(brandEntity);

//        List<BrandEntity> list = brandService.list(
//                new QueryWrapper<BrandEntity>()
//                        .eq("brand_id", 1L));
//        for (BrandEntity brandEntity : list) {
//            System.out.println(brandEntity);
//        }

    }

}
