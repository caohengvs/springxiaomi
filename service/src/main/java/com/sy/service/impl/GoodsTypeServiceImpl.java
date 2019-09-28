package com.sy.service.impl;

import com.sy.mapper.GoodsTypeMapper;
import com.sy.pojo.GoodsType;
import com.sy.service.GoodsTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ssssyy
 * 2019/9/26 11:01
 */
@Service
public class GoodsTypeServiceImpl implements GoodsTypeService {
    @Autowired
    private GoodsTypeMapper goodsTypeMapper;
    @Override
    public List<GoodsType> findTypeByLevel(int level) {
        return goodsTypeMapper.findByLevel(level);
    }
}
