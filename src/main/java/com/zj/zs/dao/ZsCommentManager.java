package com.zj.zs.dao;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zj.zs.domain.dto.request.CommentSubmitReqDto;
import com.zj.zs.domain.entity.ZsCommentDO;

import java.util.List;

/**
 * @ClassName ZsCommentManager
 * @Author zj
 * @Description
 * @Date 2024/4/20 21:54
 * @Version v1.0
 **/
public interface ZsCommentManager extends IService<ZsCommentDO> {

    List<ZsCommentDO> getByArticleId(String articleId);

    ZsCommentDO getByCommentId(String commentId);

    Boolean submitCommentByParentId(CommentSubmitReqDto request);

    Boolean saveOneLevelComment(CommentSubmitReqDto request);

    boolean updateLikeNum(String commentId);
}
