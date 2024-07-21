package com.zj.zs.domain.dto.ip;

import lombok.Data;

/**
 * @ClassName IPQueryResultDto
 * @Author zj
 * @Description
 * @Date 2024/7/21 16:56
 * @Version v1.0
 **/
@Data
public class IPQueryResultDto {

    private int code;

    private String msg;

    private IPDetailDto data;
}
