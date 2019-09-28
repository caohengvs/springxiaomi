package com.sy.mapper;

import com.sy.pojo.Goods;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * ssssyy
 * 2019/9/26 10:13
 */
public interface GoodsMapper {
    long getCount(String condition);

    List<Goods> findPageByWhere(@Param("condition") String condition);

    Goods findById(int gid);
}
