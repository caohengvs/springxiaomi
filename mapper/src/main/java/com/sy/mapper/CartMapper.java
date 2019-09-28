package com.sy.mapper;

import com.sy.pojo.Cart;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * ssssyy
 * 2019/9/26 10:13
 */
public interface CartMapper {
    Cart findByUidAndPid(@Param("uid") int uid,@Param("pid") int pid);

    void add(Cart cart);

    void update(Cart cart);

    List<Cart> findByUid(int uid);

    void delete(@Param("uid") int uid,@Param("pid") int pid);

    void deleteByUid(int uid);
}
