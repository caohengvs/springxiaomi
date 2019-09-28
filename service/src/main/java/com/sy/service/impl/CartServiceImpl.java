package com.sy.service.impl;

import com.sy.mapper.CartMapper;
import com.sy.pojo.Cart;
import com.sy.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ssssyy
 * 2019/9/26 11:01
 */
@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private CartMapper cartMapper;
    @Override
    public Cart findByUidAndPid(int uid, int pid) {
        return cartMapper.findByUidAndPid(uid,pid);
    }

    @Override
    public void add(Cart cart) {
            cartMapper.add(cart);
    }

    @Override
    public void update(Cart cart) {
            cartMapper.update(cart);
    }

    @Override
    public List<Cart> findByUid(int uid) {
        return cartMapper.findByUid(uid);
    }

    @Override
    public void delete(int uid, int pid) {
        cartMapper.delete(uid,pid);
    }

    @Override
    public void deleteByUid(int uid) {
        cartMapper.deleteByUid(uid);
    }
}
