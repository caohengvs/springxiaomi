package com.sy.service.impl;

import com.sy.mapper.GoodsMapper;
import com.sy.mapper.GoodsTypeMapper;
import com.sy.pojo.Goods;
import com.sy.pojo.GoodsType;
import com.sy.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ssssyy
 * 2019/9/26 11:01
 */
@Service
public class GoodsServiceImpl implements GoodsService {
    @Autowired
    private GoodsMapper goodsMapper;
    @Autowired
    private GoodsTypeMapper goodsTypeMapper;

    //     待完成
    @Override
    public List<Goods> findPageByWhere(String condition) {
        return goodsMapper.findPageByWhere(condition);
    }


    @Override
    public Goods findById(int gid) {
        Goods goods = goodsMapper.findById(gid); //goodsType null
        //更加商品的类型id，查询商品类型
        GoodsType goodsType = goodsTypeMapper.findById(goods.getTypeid());
        goods.setGoodsType(goodsType);
        return goods;
    }
}
