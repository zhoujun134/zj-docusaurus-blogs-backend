package com.zj.zs.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @ClassName ZsFriendsDO
 * @Author zj
 * @Description
 * @Date 2024/4/20 15:06
 * @Version v1.0
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("zs_friends")
public class ZsFriendsDO extends BaseDO {

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 网站标题
     */
    @TableField(value = "title")
    private String title;

    /**
     * 网站地址
     */
    private String siteUrl;

    /**
     * 网站 logo 图地址
     */
    private String logoUrl;

    /**
     * 网站描述
     */
    private String description;

    /**
     * 网站分类
     * @link com.zj.zs.constants.FriendsCategoryEnum
     */
    private String categoryCode;

    /**
     * 网站管理者联系邮箱
     */
    private String email;

}
