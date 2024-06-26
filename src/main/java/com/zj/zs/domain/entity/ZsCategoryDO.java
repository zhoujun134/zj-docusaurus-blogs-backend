package com.zj.zs.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @ClassName CategoryDO
 * @Author zj
 * @Description
 * @Date 2024/4/14 20:56
 * @Version v1.0
 **/
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("zs_category")
@AllArgsConstructor
@NoArgsConstructor
public class ZsCategoryDO extends BaseDO {

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 分类 id
     */
    private String categoryId;
    /**
     * 分类名称
     */
    private String name;

    public ZsCategoryDO(String categoryId, String name) {
        this.categoryId = categoryId;
        this.name = name;
    }
}
