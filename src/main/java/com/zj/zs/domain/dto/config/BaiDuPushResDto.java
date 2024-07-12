package com.zj.zs.domain.dto.config;

import lombok.Data;

import java.util.List;

@Data
public class BaiDuPushResDto {

    /**
     * 成功推送的url条数
     */
    private Integer success;

    /**
     * 当天剩余的可推送url条数
     */
    private Integer remain;

    /**
     * 由于不是本站url而未处理的url列表
     */
    private List<String> not_same_site;
    /**
     * 不合法的url列表
     */
    private List<String> not_valid;
}
