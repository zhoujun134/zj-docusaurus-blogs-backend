package com.zj.zs.converter;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.zj.zs.domain.dto.article.ArticleDto;
import com.zj.zs.domain.dto.article.CategoryDto;
import com.zj.zs.domain.dto.article.CommentDto;
import com.zj.zs.domain.dto.article.TagDto;
import com.zj.zs.domain.dto.page.ZsFriendsDto;
import com.zj.zs.domain.dto.request.CommentSubmitReqDto;
import com.zj.zs.domain.dto.request.admin.AddArticleReqDto;
import com.zj.zs.domain.entity.*;
import com.zj.zs.utils.Safes;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName ArticleConverter
 * @Author zj
 * @Description
 * @Date 2024/3/17 17:38
 * @Version v1.0
 **/
@Mapper(componentModel = "spring")
public interface ArticleConverter {

    @Mapping(target = "tagList", ignore = true)
    @Mapping(target = "headerImageUrl", ignore = true)
    ArticleDto toArticleDTO(ZsArticleDO zsArticleDO);

    @Mapping(target = "articleId", expression = "java(com.zj.zs.utils.UUIDUtils.uuid())")
    @Mapping(target = "createTime", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "updateUsername", ignore = true)
    @Mapping(target = "updateTime", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "isDelete", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createUsername", ignore = true)
    ZsArticleDO toZsArticleDO(AddArticleReqDto dto);

    CategoryDto toCategoryDTO(ZsCategoryDO zsCategoryDO);

    @Mapping(target = "tagName", source = "name")
    TagDto toTagDTO(ZsTagDO zsTagDO);

    @Mapping(target = "categoryName", expression = "java(com.zj.zs.constants.FriendsCategoryEnum.getNameByCode(zsFriends.getCategoryCode()))")
    ZsFriendsDto toZsFriendsDto(ZsFriendsDO zsFriends);

    @Mapping(target = "children", ignore = true)
    @Mapping(target = "createTime", source = "createTime")
    CommentDto toCommentDto(ZsCommentDO zsCommentDO);

    @Mapping(target = "likeNum", ignore = true)
    @Mapping(target = "replyList", ignore = true)
    @Mapping(target = "replyIdList", ignore = true)
    @Mapping(target = "createTime", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "updateUsername", ignore = true)
    @Mapping(target = "updateTime", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "isDelete", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createUsername", ignore = true)
    @Mapping(target = "parentId", source = "parentCommentId")
    @Mapping(target = "replyIds", expression = "java(\"\")")
    @Mapping(target = "commentId", expression = "java(com.zj.zs.utils.UUIDUtils.uuid())")
    ZsCommentDO toCommentDO(CommentSubmitReqDto request);

    @Mapping(target = "createUsername", ignore = true)
    @Mapping(target = "createTime", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "updateUsername", ignore = true)
    @Mapping(target = "updateTime", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "isDelete", ignore = true)
    @Mapping(target = "id", ignore = true)
    ZsRefArticleCommentDO toZsRefArticleCommentDO(String articleId, String commentId, String commentLevel);
    default List<TagDto> convertTagList(List<ZsTagDO> tagDOList) {
        if (CollectionUtils.isEmpty(tagDOList)) {
            return Collections.emptyList();
        }
        return Safes.of(tagDOList).stream()
                .map(this::toTagDTO)
                .collect(Collectors.toList());
    }
    default List<CategoryDto> convertCategpryList(List<ZsCategoryDO> tagDOList) {
        if (CollectionUtils.isEmpty(tagDOList)) {
            return Collections.emptyList();
        }
        return Safes.of(tagDOList).stream()
                .map(this::toCategoryDTO)
                .collect(Collectors.toList());
    }



}
