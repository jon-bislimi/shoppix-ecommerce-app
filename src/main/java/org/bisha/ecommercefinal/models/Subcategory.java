package org.bisha.ecommercefinal.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "subcategories")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Subcategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @NotBlank(message = "Name is mandatory")
    @Size(max = 100, message = "Name must be less than or equal to 100 characters")
    @Column(name = "name")
    private String name;

    @Size(max = 255, message = "Description must be less than or equal to 255 characters")
    @Column(name = "description")
    private String description;

    @NotNull(message = "Parent category cannot be null")
    @ManyToOne
    @JoinColumn(name = "parent_category_id")
    private Category parentCategory;
}