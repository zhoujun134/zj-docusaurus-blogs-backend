package com.zj.zs.domain.dto.openai;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @ClassName OpenApiRes
 * @Author zj
 * @Description
 * @Date 2024/4/27 12:41
 * @Version v1.0
 **/
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class OpenApiResDto {

    private String code = "0";

    private String message = "ok";

    private String resContent;

    public OpenApiResDto(String resContent) {
        this.resContent = resContent;
    }
}
