package com.zj.zs.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName DictionaryDto
 * @Author zj
 * @Description
 * @Date 2024/4/11 21:56
 * @Version v1.0
 **/
@Data
@TableName("zs_dictionary")
@AllArgsConstructor
@NoArgsConstructor
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

    public ZsDictionaryDO(String key, String value) {
        this.key = key;
        this.value = value;
    }
}
