package com.zj.zs.domain.dto.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @ClassName RegisterUserInfoReqDTO
 * @Author zj
 * @Description
 * @Date 2024/3/10 16:03
 * @Version v1.0
 **/
@Data
public class RegisterUserInfoReqDTO {

    private String name;

    @NotBlank(message = "用户名不能为空")
    private String username;

    @NotBlank(message = "密码不能为空")
    private String password;

    @NotBlank(message = "邮箱不能为空")
    private String email;

    private String description;

}
