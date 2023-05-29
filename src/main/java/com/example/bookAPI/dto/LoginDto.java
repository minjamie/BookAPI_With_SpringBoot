package com.example.bookAPI.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class LoginDto {

    @NotNull
    @Size(min = 3, max = 50)
    private String email;

    @NonNull
    @Size(min = 3, max = 100)
    private String password;


}
