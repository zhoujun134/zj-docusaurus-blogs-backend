package com.zj.zs.domain.dto.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @ClassName CommentSubmitReqDto
 * @Author zj
 * @Description
 * @Date 2024/4/20 22:58
 * @Version v1.0
 **/
@Data
public class CommentSubmitReqDto {

    private String articleId;

    private String parentCommentId;

    private String replyCommentId;

    @NotBlank(message = "提交昵称不能为空")
    private String author;

    @NotBlank(message = "评论邮箱不能为空")
    private String email;

    @NotBlank(message = "评论内容不能为空")
    private String content;
}
