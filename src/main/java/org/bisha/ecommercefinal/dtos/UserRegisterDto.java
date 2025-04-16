package org.bisha.ecommercefinal.dtos;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bisha.ecommercefinal.enums.Role;

import java.time.LocalDate;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterDto {
    @NotBlank(message = "Name is mandatory")
    @Size(max = 100, message = "Name must be less than or equal to 100 characters")
    private String name;

    @NotBlank(message = "Username is mandatory")
    @Size(max = 50, message = "Username must be less than or equal to 50 characters")
    private String username;

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Password is mandatory")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d).+$", message = "Password must contain at least one uppercase letter, one lowercase letter, and one digit")
    private String password;

    @NotBlank(message = "Confirm password is mandatory")
    @Size(min = 8, message = "Confirm password must be at least 8 characters long")
    private String confirmPassword;

    @NotNull(message = "Role cannot be null")
    private Role role = Role.ROLE_USER;

    @Past(message = "Birth date must be in the past")
    private LocalDate birthDate;

    @Size(max = 255, message = "Address must be less than or equal to 255 characters")
    private String address;

    @Size(max = 255, message = "Profile picture URL must be less than or equal to 255 characters")
    private String profilePictureURL;

    @Pattern(regexp = "\\d{10,15}", message = "Telephone number must be between 10 and 15 digits")
    private String telephoneNumber;
}