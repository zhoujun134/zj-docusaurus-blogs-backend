package com.zj.zs.domain.dto.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccessIpConfigDto {

    private String ip;

    private List<String> userAgents;

    private List<String>  urls;

    private List<String>  refererList;
}
