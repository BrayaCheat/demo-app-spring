package com.example.bookStore.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer categoryId;

    @Column(nullable = false)
    @NotBlank(message = "Category is mandatory")
    @Size(max = 100, message = "Category should not exceeds 100 characters")
    private String name;

    @JsonIgnore
    @CreationTimestamp
    @Column(nullable = false, name = "created_at")
    private LocalDateTime created_at;

    @JsonIgnore
    @UpdateTimestamp
    @Column(nullable = false, name = "updated_at")
    private LocalDateTime updated_at;

    @JsonIgnore
    @ManyToMany(mappedBy = "categories")
    private Set<Book> books = new HashSet<>();
}
