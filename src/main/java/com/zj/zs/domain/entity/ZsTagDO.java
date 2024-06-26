package com.zj.zs.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @ClassName ZsTagDO
 * @Author zj
 * @Description
 * @Date 2024/4/14 22:19
 * @Version v1.0
 **/
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("zs_tag")
@AllArgsConstructor
@NoArgsConstructor
public class ZsTagDO extends BaseDO {

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 标签 id
     */
    private String tagId;
    /**
     * 标签名称
     */
    private String name;

    public ZsTagDO(String tagId, String tagName) {
        this.tagId = tagId;
        this.name = tagName;
    }
}
