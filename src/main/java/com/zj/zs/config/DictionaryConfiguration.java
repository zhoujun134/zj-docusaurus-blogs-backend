package com.zj.zs.config;

import com.zj.zs.constants.GlobalConstants;
import com.zj.zs.dao.DictionaryManager;
import com.zj.zs.domain.dto.docusaurus.DocusaurusPublishShellConfigDto;
import com.zj.zs.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * @ClassName KvConfiguration
 * @Author zj
 * @Description
 * @Date 2024/6/30 16:36
 * @Version v1.0
 **/
@Configuration
@Slf4j
public class DictionaryConfiguration {
    @Resource
    private DictionaryManager dictionaryManager;
    @PostConstruct
    public void initDocusaurusConfig() {
        String dictionaryConfigString = dictionaryManager.getByKey(GlobalConstants.DOCUSAURUS_CONFIG_DICT_KEY);
        if (StringUtils.isBlank(dictionaryConfigString)) {
            log.warn("##initDocusaurusConfig: {} dictionaryConfig is not set", GlobalConstants.DOCUSAURUS_CONFIG_DICT_KEY);
            return;
        }
        DocusaurusPublishShellConfigDto configDto = JsonUtils.parseObject(dictionaryConfigString, DocusaurusPublishShellConfigDto.class);
        log.info("##initDocusaurusConfig: 数据库加载出来 DocusaurusPublishShellConfigDto={}",  JsonUtils.toString(configDto));
        GlobalConstants.docusaurusPublishConfig = configDto;
    }
}
