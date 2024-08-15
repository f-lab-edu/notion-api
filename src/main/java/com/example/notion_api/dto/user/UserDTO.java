package com.example.notion_api.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDTO {

    /**
     *  회원가입, 회원정보 수정에 사용됨.
     * */

    @NotBlank
    @Size(min = 5, max = 20)
    private String userId;

    @NotBlank
    @Size(min = 8)
    private String password;

    @NotBlank
    private String name;

    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$")
    private String birth;

    @Pattern(regexp = "^\\d{10,15}$")
    private String phoneNumber;

    @Email
    private String email;
}
