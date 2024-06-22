package com.zj.zs.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @ClassName BaseDBDO
 * @Author zj
 * @Description
 * @Date 2024/4/14 21:00
 * @Version v1.0
 **/
@Data
public class BaseDBDO {

    private String createUsername;

    private String updateUsername;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    @TableLogic
    @TableField(value = "deleted")
    private Boolean isDelete;
}
