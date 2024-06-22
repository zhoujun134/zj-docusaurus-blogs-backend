package com.zj.zs.domain.dto.article;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @ClassName ZsCommentDto
 * @Author zj
 * @Description
 * @Date 2024/4/20 22:03
 * @Version v1.0
 **/
@Data
public class CommentDto {

    private String commentId;
    private String author;
    private String email;
    private String content;
    private Long likeNum;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    private List<CommentDto> children;
}
