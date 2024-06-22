package com.zj.zs.domain.dto.openai;

import lombok.Data;

/**
 * @ClassName NvidiaSuccessResponseChoicesEntityDto
 * @Author zj
 * @Description
 * @Date 2024/4/27 12:52
 * @Version v1.0
 **/
@Data
public class NvidiaSuccessResponseChoicesEntityDto {
    private Integer index;
    private NvidiaModelMessageDto message;
}
