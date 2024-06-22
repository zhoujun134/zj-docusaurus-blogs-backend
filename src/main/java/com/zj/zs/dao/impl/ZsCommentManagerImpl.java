package com.zj.zs.dao.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zj.zs.converter.ArticleConverter;
import com.zj.zs.dao.ZsCommentManager;
import com.zj.zs.dao.ZsRefArticleCommentManager;
import com.zj.zs.dao.mapper.ZsCommentMapper;
import com.zj.zs.domain.dto.request.CommentSubmitReqDto;
import com.zj.zs.domain.entity.ZsCommentDO;
import com.zj.zs.domain.entity.ZsRefArticleCommentDO;
import com.zj.zs.utils.Safes;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.zj.zs.constants.CommentLevelEnum.*;

/**
 * @ClassName ZsCommentManagerImpl
 * @Author zj
 * @Description
 * @Date 2024/4/20 21:54
 * @Version v1.0
 **/
@Slf4j
@Component
public class ZsCommentManagerImpl extends ServiceImpl<ZsCommentMapper, ZsCommentDO> implements ZsCommentManager {

    @Resource
    private ZsRefArticleCommentManager zsRefArticleCommentManager;

    @Resource
    private ArticleConverter articleConverter;

    @Override
    public List<ZsCommentDO> getByArticleId(String articleId) {
        if (StringUtils.isBlank(articleId)) {
            return Collections.emptyList();
        }
        List<ZsRefArticleCommentDO> refInfo = zsRefArticleCommentManager.getOneLevelByArticleId(articleId);
        List<String> commentIds = Safes.of(refInfo).stream()
                .map(ZsRefArticleCommentDO::getCommentId)
                .distinct()
                .toList();
        if (CollectionUtils.isEmpty(commentIds)) {
            return Collections.emptyList();
        }
        List<ZsCommentDO> comments = lambdaQuery().eq(ZsCommentDO::getIsDelete, false)
                .in(ZsCommentDO::getCommentId, commentIds)
                .orderByDesc(ZsCommentDO::getCreateTime)
                .list();
        if (CollectionUtils.isEmpty(comments)) {
            return Collections.emptyList();
        }
        List<String> refComments = Safes.of(comments).stream()
                .map(ZsCommentDO::getReplyIdList)
                .flatMap(List::stream)
                .distinct()
                .toList();
        if (CollectionUtils.isEmpty(refComments)) {
            return comments;
        }
        List<ZsCommentDO> replyComments = lambdaQuery()
                .in(ZsCommentDO::getCommentId, refComments)
                .list();
        Map<String, ZsCommentDO> replyMap = Safes.of(replyComments).stream()
                .collect(Collectors.toMap(ZsCommentDO::getCommentId, Function.identity(), (x, y) -> x));

        Safes.of(comments).forEach(comment -> {
            List<String> replyIdList = comment.getReplyIdList();
            if (CollectionUtils.isEmpty(replyIdList)) {
                return;
            }
            List<ZsCommentDO> replyCommentList = Safes.of(replyIdList).stream()
                    .map(replyId -> {
                        if (replyMap.containsKey(replyId)) {
                            return replyMap.get(replyId);
                        }
                        return null;
                    }).filter(Objects::nonNull)
                    .toList();
            comment.setReplyList(replyCommentList);
        });
        return comments;
    }

    @Override
    public ZsCommentDO getByCommentId(String commentId) {
        if (StringUtils.isBlank(commentId)) {
            return null;
        }
        List<ZsCommentDO> result = lambdaQuery()
                .eq(ZsCommentDO::getCommentId, commentId)
                .list();
        return Safes.first(result);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean submitCommentByParentId(CommentSubmitReqDto request) {
        ZsCommentDO parentComment = this.getByCommentId(request.getParentCommentId());
        if (Objects.isNull(parentComment)) {
            return Boolean.FALSE;
        }
        ZsCommentDO needSaveComment = articleConverter.toCommentDO(request);
        boolean saveRes = this.save(needSaveComment);
        // 父 ID 和 回复 ID 相同
        if (!StringUtils.equals(request.getReplyCommentId(), request.getParentCommentId())) {
            ZsCommentDO replayComment = this.getByCommentId(request.getReplyCommentId());
            replayComment.updateReplyIds(needSaveComment.getCommentId());
            this.updateById(replayComment);
        }
        parentComment.updateReplyIds(needSaveComment.getCommentId());
        this.updateById(parentComment);
        // 关联表信息更新
        ZsRefArticleCommentDO zsRefArticleComment = articleConverter.toZsRefArticleCommentDO(request.getArticleId(),
                needSaveComment.getCommentId(), DEFAULT_LEVEL.getCode());
        if (StringUtils.equals(request.getReplyCommentId(), request.getParentCommentId())) {
            zsRefArticleComment.setCommentLevel(TWO_LEVEL.getCode());
        } else {
            zsRefArticleComment.setCommentLevel(MANY_LEVEL.getCode());
        }
        zsRefArticleCommentManager.save(zsRefArticleComment);
        return saveRes;
    }

    @Override
    public Boolean saveOneLevelComment(CommentSubmitReqDto request) {
        ZsCommentDO needSaveComment = articleConverter.toCommentDO(request);
        boolean saveRes = this.save(needSaveComment);
        // 关联表信息更新
        ZsRefArticleCommentDO zsRefArticleComment = articleConverter.toZsRefArticleCommentDO(request.getArticleId(),
                needSaveComment.getCommentId(), ONE_LEVEL.getCode());
        zsRefArticleCommentManager.save(zsRefArticleComment);
        return saveRes;
    }

    @Override
    public boolean updateLikeNum(String commentId) {
        if (StringUtils.isBlank(commentId)) {
            return Boolean.FALSE;
        }
        ZsCommentDO commentInfo = this.getByCommentId(commentId);
        Long likeNum = commentInfo.getLikeNum();
        if (Objects.isNull(likeNum)) {
            likeNum = 1L;
        } else {
            likeNum += 1;
        }
        commentInfo.setLikeNum(likeNum);
        return lambdaUpdate()
                .set(ZsCommentDO::getLikeNum, likeNum)
                .eq(ZsCommentDO::getCommentId, commentId)
                .update();

    }
}
