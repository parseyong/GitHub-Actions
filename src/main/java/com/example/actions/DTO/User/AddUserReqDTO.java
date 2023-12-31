package com.example.actions.DTO.User;

import com.example.actions.domain.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddUserReqDTO {

    @ApiModelProperty(value = "유저 이메일")
    @NotBlank
    @Email
    private String email;

    @ApiModelProperty(value = "유저 이름")
    @NotBlank
    private String name;

    @ApiModelProperty(value = "유저 비밀번호")
    @NotBlank
    private String password;

    public static User toEntity(AddUserReqDTO addUserReqDTO){

        return User.builder()
                .name(addUserReqDTO.getName())
                .email(addUserReqDTO.getEmail())
                .password(addUserReqDTO.getPassword())
                .build();

    }
}
