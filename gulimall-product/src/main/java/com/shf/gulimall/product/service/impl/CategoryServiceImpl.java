package com.shf.gulimall.product.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shf.common.utils.PageUtils;
import com.shf.common.utils.Query;

import com.shf.gulimall.product.dao.CategoryDao;
import com.shf.gulimall.product.entity.CategoryEntity;
import com.shf.gulimall.product.service.CategoryService;


@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {

//    @Autowired
//    CategoryDao categoryDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryEntity> page = this.page(
                new Query<CategoryEntity>().getPage(params),
                new QueryWrapper<CategoryEntity>()
        );

        return new PageUtils(page);
    }

    /**
     * 查出所有分类以及子分类，以树形结构组装起来
     */
    @Override
    public List<CategoryEntity> listWithTree() {
//        1.查出所有分类
        List<CategoryEntity> entities = baseMapper.selectList(null);

//        2.组装成父子的树形解构
//        2.1找到所有的一级分类
        List<CategoryEntity> level1Menus = entities.stream().filter(categoryEntity ->
             categoryEntity.getParentCid() == 0
        ).map((menu)->{
            menu.setChildren(getChildrens(menu,entities));
            return menu;
        }).sorted((menu1,menu2)->menu1.getSort()-menu2.getSort())
                .collect(Collectors.toList());
        return level1Menus;
    }

    // 删除菜单
    @Override
    public void removeMenuByIds(List<Long> asList) {
        // TODO: 2022/1/25 1.检查当前删除的菜单，是否被别的地方引用
//        直接删除不推荐，推荐逻辑删除
        baseMapper.deleteBatchIds(asList);
    }

    //    递归查找所有菜单的子菜单
    private List<CategoryEntity> getChildrens(CategoryEntity root,List<CategoryEntity> all){
        List<CategoryEntity> children = all.stream().filter(categoryEntity ->
                categoryEntity.getParentCid().equals(root.getCatId())
        ).map(categoryEntity -> {
//            1.找到子菜单
            categoryEntity.setChildren(getChildrens(categoryEntity, all));
            return categoryEntity;
//            2.菜单的排序
        }).sorted((menu1,menu2)->
                (menu1.getSort()==null?0:menu1.getSort()) - (menu2.getSort()==null?0: menu2.getSort())
        ).collect(Collectors.toList());
        return children;
    }
}