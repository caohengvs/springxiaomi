package com.sy.service;

import com.sy.pojo.Address;

import java.util.List;

/**
 * ssssyy
 * 2019/9/26 11:00
 */
public interface AddressService {
    List<Address> findByUid(Integer id);

    void add(Address address);

    void updateDefault(int aid,int uid);

    void delete(int parseInt);

    void update(Address address);
}
