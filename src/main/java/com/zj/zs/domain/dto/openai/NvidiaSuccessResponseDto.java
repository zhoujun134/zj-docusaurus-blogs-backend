package com.zj.zs.domain.dto.openai;

import com.google.common.collect.Lists;
import lombok.Data;

import java.util.List;

/**
 * @ClassName NvidiaSuccessResponseDto
 * @Author zj
 * @Description
 * @Date 2024/4/27 12:49
 * @Version v1.0
 **/
@Data
public class NvidiaSuccessResponseDto {
    private String id;
    private String object;
    private Long created;
    private String model;
    private List<NvidiaSuccessResponseChoicesEntityDto> choices = Lists.newArrayList();
    private NvidiaSuccessResponseUsageDto usage;

}
