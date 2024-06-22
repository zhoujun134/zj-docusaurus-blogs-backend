package com.zj.zs.domain.dto.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @ClassName CommentLikeSubmitReqDto
 * @Author zj
 * @Description
 * @Date 2024/4/21 10:53
 * @Version v1.0
 **/
@Data
public class CommentLikeSubmitReqDto {
    @NotBlank(message = "评论ID 不能为空")
    private String commentId;
}
