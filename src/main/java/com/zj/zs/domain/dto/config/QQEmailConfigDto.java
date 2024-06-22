package com.zj.zs.domain.dto.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

/**
 * @ClassName EmailConfigDto
 * @Author zj
 * @Description
 * @Date 2024/4/11 22:16
 * @Version v1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QQEmailConfigDto {

    private String userSenderEmail;

    private String userName;

    private String userPassword;

    public boolean isBlankUserNameAndPassword() {
        return StringUtils.isAnyBlank(userName, userPassword);
    }
}
