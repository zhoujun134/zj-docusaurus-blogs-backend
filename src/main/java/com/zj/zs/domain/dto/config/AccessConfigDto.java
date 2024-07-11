package com.zj.zs.domain.dto.config;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class AccessConfigDto {

    private List<String> oneLevelBlackIpList = Lists.newArrayList();

    private Map<String, AccessIpConfigDto> blackIpConfigMap = Maps.newHashMap();

}
