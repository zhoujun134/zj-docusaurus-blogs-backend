package com.zj.zs.dao.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zj.zs.dao.ZsRefArticleCommentManager;
import com.zj.zs.dao.mapper.ZsRefArticleCommentMapper;
import com.zj.zs.domain.entity.ZsRefArticleCommentDO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

import static com.zj.zs.constants.CommentLevelEnum.ONE_LEVEL;

/**
 * @ClassName ZsRefArticleCommentManagerImpl
 * @Author zj
 * @Description
 * @Date 2024/4/20 22:00
 * @Version v1.0
 **/
@Slf4j
@Component
public class ZsRefArticleCommentManagerImpl  extends ServiceImpl<ZsRefArticleCommentMapper, ZsRefArticleCommentDO> implements ZsRefArticleCommentManager {
    @Override
    public List<ZsRefArticleCommentDO> getOneLevelByArticleId(String articleId) {
        if (StringUtils.isBlank(articleId)) {
            return Collections.emptyList();
        }
        return lambdaQuery()
                .eq(ZsRefArticleCommentDO::getArticleId, articleId)
                .eq(ZsRefArticleCommentDO::getCommentLevel, ONE_LEVEL.getCode())
                .list();
    }
}
