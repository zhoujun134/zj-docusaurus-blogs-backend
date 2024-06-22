package com.zj.zs.domain.dto.article;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @ClassName ArchivistDto
 * @Author zj
 * @Description
 * @Date 2024/5/4 16:59
 * @Version v1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArchivistDto {

    private String articleId;

    private String title;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    private String createTimeStr;
}
