package org.bisha.ecommercefinal.dtos;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bisha.ecommercefinal.enums.Role;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDto {
    @NotNull(message = "Id is mandatory")
    @PositiveOrZero(message = "Id must be a positive number")
    private Long id;

    @NotBlank(message = "Name is mandatory")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    private String name;

    @NotBlank(message = "Username is mandatory")
    @Size(min = 2, max = 30, message = "Username must be between 2 and 30 characters")
    private String username;

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email should be valid")
    private String email;

    private Role role;

    @NotNull(message = "Birth date is mandatory")
    @Past(message = "Birth date must be in the past")
    private LocalDate birthDate;

    @NotBlank(message = "Address is mandatory")
    @Size(min = 10, max = 200, message = "Address must be between 10 and 200 characters")
    private String address;

    @NotBlank(message = "Profile picture URL is mandatory")
    @Pattern(regexp = "^(http|https)://.*$", message = "Profile picture URL must be a valid URL")
    private String profilePictureURL;

    @PastOrPresent(message = "Created at date must be in the past or present")
    private LocalDateTime createdAt;

    @Pattern(regexp = "^\\+?[0-9. ()-]{7,25}$", message = "Telephone number must be a valid phone number")
    private String telephoneNumber;

    public String getRoleDisplayName() {
        return role.getDisplayName();
    }
}