package com.zj.zs.domain.dto.page;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @ClassName ZsPageDTO
 * @Author zj
 * @Description
 * @Date 2024/3/17 17:32
 * @Version v1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ZsPageDto<T> {

    private long current;

    private long total;

    private long pageSize;

    private List<T> records;
}
