package com.sy.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * ssssyy
 * 2019/9/26 10:06
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Cart {
    private Integer id;
    private Integer pid;
    private Goods goods;
    private Integer num;
    private BigDecimal money;

    public Cart(Integer id, Integer pid, Integer num, BigDecimal money) {
        this.id = id;
        this.pid = pid;
        this.num = num;
        this.money = money;
    }
}
