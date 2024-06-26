package com.zj.zs.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.Collections;
import java.util.List;

/**
 * @ClassName ZsCommentDO
 * @Author zj
 * @Description
 * @Date 2024/4/20 21:17
 * @Version v1.0
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("zs_comment")
@AllArgsConstructor
@NoArgsConstructor
public class ZsCommentDO extends BaseDO {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String commentId;
    private String author;
    private String email;
    private String content;
    private String parentId;

    private Long likeNum;

    private String replyIds;

    @TableField(exist = false)
    private List<String> replyIdList = getReplyIdList();


    @TableField(exist = false)
    private List<ZsCommentDO> replyList = Lists.newArrayList();

    public List<String> getReplyIdList() {
        if (StringUtils.isBlank(replyIds)) {
            return Collections.emptyList();
        }
        return replyIdList = Lists.newArrayList(replyIds.split(","));
    }

    public void updateReplyIds(String commentId) {
        if (StringUtils.isBlank(this.getReplyIds())) {
            this.setReplyIds(commentId);
        } else {
            this.setReplyIds(this.getReplyIds() + "," + commentId);
        }
    }

}
