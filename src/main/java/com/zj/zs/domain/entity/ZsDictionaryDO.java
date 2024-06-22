package com.zj.zs.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

/**
 * @ClassName DictionaryDto
 * @Author zj
 * @Description
 * @Date 2024/4/11 21:56
 * @Version v1.0
 **/
@Data
@TableName("zs_dictionary")
public class ZsDictionaryDO {

    @TableId(type = IdType.AUTO)
    private Long id;

    @TableField(value = "`key`")
    private String key;

    @TableField(value = "`value`")
    private String value;

    @TableLogic
    @TableField(value = "deleted")
    private boolean isDelete;
}
