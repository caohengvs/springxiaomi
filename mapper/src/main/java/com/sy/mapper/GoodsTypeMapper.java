package com.sy.mapper;

import com.sy.pojo.GoodsType;

import java.util.List;

/**
 * ssssyy
 * 2019/9/26 10:12
 */
public interface GoodsTypeMapper {
    List<GoodsType> findByLevel(int level);

    GoodsType findById(int typeid);
}
