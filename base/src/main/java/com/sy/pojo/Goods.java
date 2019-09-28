package com.sy.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * ssssyy
 * 2019/9/26 10:04
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Goods {
    private Integer id;
    private String name;
    private Date pubdate;
    private String picture;
    private BigDecimal price;
    private Integer star;
    private String intro;
    private Integer typeid;
    private GoodsType goodsType;
}
