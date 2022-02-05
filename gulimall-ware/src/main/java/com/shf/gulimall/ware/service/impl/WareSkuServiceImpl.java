package com.shf.gulimall.ware.service.impl;

import com.shf.common.utils.R;
import com.shf.gulimall.ware.feign.ProductFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shf.common.utils.PageUtils;
import com.shf.common.utils.Query;

import com.shf.gulimall.ware.dao.WareSkuDao;
import com.shf.gulimall.ware.entity.WareSkuEntity;
import com.shf.gulimall.ware.service.WareSkuService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;


@Service("wareSkuService")
public class WareSkuServiceImpl extends ServiceImpl<WareSkuDao, WareSkuEntity> implements WareSkuService {

    @Autowired
    WareSkuDao wareSkuDao;

    @Autowired
    ProductFeignService productFeignService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        QueryWrapper<WareSkuEntity> wareSkuEntityQueryWrapper = new QueryWrapper<>();

        String skuID = (String) params.get("key");
        if (!StringUtils.isEmpty(skuID)){
            wareSkuEntityQueryWrapper.eq("sku_id", skuID);
        }

        String wareID = (String) params.get("key");
        if (!StringUtils.isEmpty(wareID)){
            wareSkuEntityQueryWrapper.eq("ware_id", wareID);
        }

        IPage<WareSkuEntity> page = this.page(
                new Query<WareSkuEntity>().getPage(params),
                wareSkuEntityQueryWrapper
        );

        return new PageUtils(page);
    }

    @Transactional
    @Override
    public void addStock(Long skuId, Long wareId, Integer skuNum) {
//        1.判断如果还没有这个库存记录新增
        List<WareSkuEntity> wareSkuEntities = wareSkuDao.selectList(
                new QueryWrapper<WareSkuEntity>()
                        .eq("sku_id", skuId)
                        .eq("ware_id", wareId)
        );
        if (wareSkuEntities.size()==0 || wareSkuEntities==null){
            WareSkuEntity skuEntity = new WareSkuEntity();
            skuEntity.setSkuId(skuId);
            skuEntity.setStock(skuNum);
            skuEntity.setWareId(wareId);
            skuEntity.setStockLocked(0);
//            远程查询sku的名字  如果失败，整个事务无需回滚
            try {
                R info = productFeignService.info(skuId);
                Map<String,Object> data = (Map<String, Object>) info.get("data");
                if (info.getCode()==0){
                    skuEntity.setSkuName((String) data.get("skuName"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            wareSkuDao.insert(skuEntity);
        } else {
            wareSkuDao.addStock(skuId,wareId,skuNum);
        }
    }

}