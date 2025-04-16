package org.bisha.ecommercefinal.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bisha.ecommercefinal.enums.Role;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private long id;

    @NotBlank(message = "Name is mandatory")
    @Size(max = 100, message = "Name must be less than or equal to 100 characters")
    @Column(name = "name")
    private String name;

    @NotBlank(message = "Username is mandatory")
    @Size(max = 50, message = "Username must be less than or equal to 50 characters")
    @Column(name = "username", unique = true)
    private String username;

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email should be valid")
    @Column(name = "email", unique = true)
    private String email;

    @NotBlank(message = "Password is mandatory")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    @Column(name = "password")
    private String password;

    @NotNull(message = "Role cannot be null")
    @Column(name = "role")
    private Role role;

    @Past(message = "Birth date must be in the past")
    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Size(max = 255, message = "Address must be less than or equal to 255 characters")
    @Column(name = "address")
    private String address;

    @Size(max = 255, message = "Profile picture URL must be less than or equal to 255 characters")
    @Column(name = "profile_picture_url")
    private String profilePictureURL;

    @NotNull(message = "Creation date cannot be null")
    @PastOrPresent(message = "Creation date must be in the past or present")
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "user")
    private List<OrderItem> boughtProducts;

    @Pattern(regexp = "\\d{10,15}", message = "Telephone number must be between 10 and 15 digits")
    @Column(name = "telephone_number")
    private String telephoneNumber;

    @Column(name = "is_active")
    private boolean active;
}