package com.zj.zs.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("zs_user_info")
public class ZsUserDO extends BaseDO {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String name;

    private String username;

    private String password;

    private String email;

    private String description;
}
