package com.sy.mapper;

import com.sy.pojo.Address;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * ssssyy
 * 2019/9/26 10:14
 */
public interface AddressMapper {
    List<Address> findByUid(Integer uid);

    void add(Address address);

    void updateDefault(@Param("aid") int aid,@Param("uid") int uid);

    void delete(int id);

    void update(Address address);

}
