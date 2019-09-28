package com.sy.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * ssssyy
 * 2019/9/26 10:02
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    private String id;
    private Integer uid;
    private BigDecimal money;
    private String status;
    private Date time;
    private Integer aid;

}
