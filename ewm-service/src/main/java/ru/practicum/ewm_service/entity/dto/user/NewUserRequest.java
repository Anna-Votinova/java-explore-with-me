package ru.practicum.ewm_service.entity.dto.user;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@NotNull
public class NewUserRequest {
    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String name;
}
