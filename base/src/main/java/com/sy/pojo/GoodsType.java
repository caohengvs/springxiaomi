package com.sy.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ssssyy
 * 2019/9/26 10:06
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class GoodsType {
    private Integer id;
    private String name;
    private Integer level;
    private Integer parent;

}
