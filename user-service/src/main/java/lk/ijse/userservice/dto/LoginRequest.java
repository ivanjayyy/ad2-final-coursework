package lk.ijse.userservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginRequest {
    @NotBlank(message = "Please enter your email to login")
    private String email;

    @NotBlank(message = "Please enter your password to login")
    private String password;
}
