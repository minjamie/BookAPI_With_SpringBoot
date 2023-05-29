package com.example.bookAPI.dto;

import com.example.bookAPI.entity.User;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class UserDto {

    @NotNull
    @Size(min = 3, max = 50)
    private String email;

    @NonNull
    @Size(min = 3, max = 100)
    private String password;

    @NonNull
    @Size(min = 3, max = 50)
    private String name;

}
