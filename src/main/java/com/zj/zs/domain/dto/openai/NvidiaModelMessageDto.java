package com.zj.zs.domain.dto.openai;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @ClassName NvidiaModelMessageDto
 * @Author zj
 * @Description
 * @Date 2024/4/27 11:30
 * @Version v1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class NvidiaModelMessageDto {

    private String role = "user";

    private String content;
}
