package com.sy.service;

import com.sy.pojo.GoodsType;

import java.util.List;

/**
 * ssssyy
 * 2019/9/26 10:59
 */
public interface GoodsTypeService {
    List<GoodsType> findTypeByLevel(int level);
}
