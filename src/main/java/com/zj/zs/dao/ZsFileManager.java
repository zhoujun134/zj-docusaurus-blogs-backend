package com.zj.zs.dao;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zj.zs.domain.entity.ZsFileDO;

import java.util.List;

/**
 * @ClassName ImageManager
 * @Author zj
 * @Description
 * @Date 2024/4/9 23:26
 * @Version v1.0
 **/
public interface ZsFileManager extends IService<ZsFileDO> {

    List<ZsFileDO> randomOne(int pageSize);
}
