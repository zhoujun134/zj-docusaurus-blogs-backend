package com.zj.zs.domain.dto.request;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * @ClassName LoginUserReqDTO
 * @Author zj
 * @Description
 * @Date 2024/3/10 15:15
 * @Version v1.0
 **/
@Data
public class LoginUserReqDto {

    @NotBlank(message = "用户名不能为空！")
    @Length(min = 6, max = 20, message = "用户名长度在6-20之间！")
    private String username;

    @NotBlank(message = "密码不能为空！")
    @Length(min = 8, max = 20, message = "密码长度在8-20之间！")
    private String password;
}
