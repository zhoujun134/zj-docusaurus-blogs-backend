package com.zj.zs.domain.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName ArticleReqDTO
 * @Author zj
 * @Description
 * @Date 2024/3/17 17:45
 * @Version v1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleReqDto {

    private String keyword;

    private String categoryId;

    private String tagId;

    private int pageNumber = 1;

    private int pageSize = 10;

    public ArticleReqDto(int pageNumber, int pageSize) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
    }
}
