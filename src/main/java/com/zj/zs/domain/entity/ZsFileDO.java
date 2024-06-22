package com.zj.zs.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zj.zs.constants.FileSaveTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Objects;

import static com.zj.zs.constants.FileSaveTypeEnum.TENCENT_OSS;

/**
 * @ClassName ImageDO
 * @Author zj
 * @Description
 * @Date 2024/4/9 23:23
 * @Version v1.0
 **/
@Data
@TableName("zs_file")
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class ZsFileDO {
    @TableId(type = IdType.AUTO)
    private Long id;

    private String title;

    private String articleId;

    private String category;

    private String url;

    private String staticPath;

    /**
     * 文件存储的类型
     * 默认：TENCENT_OSS
     * FileSaveTypeEnum
     */
    private String fileSaveType;

    public ZsFileDO(String title, String articleId, String category, String url) {
        this.title = title;
        this.articleId = articleId;
        this.category = category;
        this.url = url;
        this.fileSaveType = TENCENT_OSS.name();
    }

    public ZsFileDO(String title, String articleId, String url, String staticPath, FileSaveTypeEnum fileSaveType) {
        this.title = title;
        this.articleId = articleId;
        this.url = url;
        this.staticPath = staticPath;
        this.fileSaveType = Objects.nonNull(fileSaveType) ? fileSaveType.name() : TENCENT_OSS.name();
    }
}
