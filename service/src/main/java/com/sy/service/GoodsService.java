package com.sy.service;

import com.sy.pojo.Goods;

import java.util.List;

/**
 * ssssyy
 * 2019/9/26 10:59
 */
public interface GoodsService {
    List<Goods> findPageByWhere(String condition);

    Goods findById(int gid);
}
