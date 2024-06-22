package com.zj.zs.domain.dto.page;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName ZsFriendsDto
 * @Author zj
 * @Description
 * @Date 2024/4/20 15:16
 * @Version v1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ZsFriendsDto {
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
    @ApiModelProperty(hidden = true)
    private String categoryName;

    /**
     * 网站管理者联系邮箱
     */
    @ApiModelProperty(hidden = true)
    private String email;
}
