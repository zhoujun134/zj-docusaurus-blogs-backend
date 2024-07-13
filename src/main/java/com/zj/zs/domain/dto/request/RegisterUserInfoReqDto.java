package com.zj.zs.domain.dto.request;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * @ClassName RegisterUserInfoReqDTO
 * @Author zj
 * @Description
 * @Date 2024/3/10 16:03
 * @Version v1.0
 **/
@Data
public class RegisterUserInfoReqDto {

    @NotBlank(message = "姓名不能为空")
    private String name;

    @NotBlank(message = "用户名不能为空")
    @Length(min = 6, max = 20, message = "用户名长度在6-20之间！")
    private String username;

    @NotBlank(message = "密码不能为空")
    @Length(min = 8, max = 20, message = "密码长度在8-20之间！")
    private String password;

    @NotBlank(message = "邮箱不能为空")
    @Length(min = 3, max = 4, message = "邮箱长度在6-20之间！")
    private String email;

    private String description;

}
