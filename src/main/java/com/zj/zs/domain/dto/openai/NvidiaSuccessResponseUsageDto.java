package com.zj.zs.domain.dto.openai;

import lombok.Data;

/**
 * @ClassName NvidiaSuccessResponseUsageDto
 * @Author zj
 * @Description
 * @Date 2024/4/27 12:50
 * @Version v1.0
 **/
@Data
public class NvidiaSuccessResponseUsageDto {

    private Integer prompt_tokens;

    private Integer total_tokens;

    private Integer completion_tokens;
}
