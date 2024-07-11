package com.zj.zs.domain.dto.request;

import lombok.Data;

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
    private String username;

    @NotBlank(message = "密码不能为空！")
    private String password;
}
