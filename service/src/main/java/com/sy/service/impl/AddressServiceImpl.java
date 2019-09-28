package com.sy.service.impl;

import com.sy.mapper.AddressMapper;
import com.sy.pojo.Address;
import com.sy.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ssssyy
 * 2019/9/26 11:02
 */
@Service
public class AddressServiceImpl implements AddressService {
    @Autowired
    private AddressMapper addressMapper;
    @Override
    public List<Address> findByUid(Integer uid) {
        return addressMapper.findByUid(uid);
    }

    @Override
    public void add(Address address) {
        addressMapper.add(address);
    }

    @Override
    public void updateDefault(int aid, int uid) {
        addressMapper.updateDefault(aid,uid);
    }

    @Override
    public void delete(int id) {
        addressMapper.delete(id);
    }

    @Override
    public void update(Address address) {
        addressMapper.update(address);
    }
}
