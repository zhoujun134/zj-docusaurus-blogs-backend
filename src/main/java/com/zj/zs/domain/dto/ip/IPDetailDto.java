package com.zj.zs.domain.dto.ip;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @ClassName IPDetailDto
 * @Author zj
 * @Description
 * @Date 2024/7/21 16:54
 * @Version v1.0
 **/
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class IPDetailDto {

    /**
     * ip
     */
    private String ip;

    /**
     * 地址
     */
    private String address;
}
