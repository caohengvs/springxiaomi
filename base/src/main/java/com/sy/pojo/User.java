package com.sy.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ssssyy
 * 2019/9/26 10:00
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Integer id;
    private String username;
    private String password;
    private String email;
    private String gender;
    private Integer flag; // 0 没有激活 1 表示激活 2 表示删除
    private Integer role; // 0 管理员 1 普通用户
    private String code;
}
