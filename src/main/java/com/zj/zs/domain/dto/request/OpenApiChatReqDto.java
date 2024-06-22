package com.zj.zs.domain.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName OpenApiChatReq
 * @Author zj
 * @Description
 * @Date 2024/4/27 10:32
 * @Version v1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OpenApiChatReqDto {

    private String text;
}
