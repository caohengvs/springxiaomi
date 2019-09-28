package com.sy.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ssssyy
 * 2019/9/26 10:07
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Address {
    private Integer id;
    private String detail;
    private String name;
    private String phone;
    private Integer uid;
    private Integer level; //0 可选地址 1 默认地址
}
