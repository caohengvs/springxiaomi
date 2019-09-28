package com.sy.service;

import com.sy.pojo.Cart;

import java.util.List;

/**
 * ssssyy
 * 2019/9/26 11:00
 */
public interface CartService {
    Cart findByUidAndPid(int uid, int pid);

    void add(Cart cart);

    void update(Cart cart);

    List<Cart> findByUid(int uid);

    void delete(int uid, int pid);

    void deleteByUid(int uid);
}
