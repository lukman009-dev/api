package com.shehia_management.api.entity;

import jakarta.persistence.*;
import lombok.*;
import com.shehia_management.api.enums.Role;
import com.shehia_management.api.enums.UserStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String zanId;

    @Column(nullable = false)
    private String fullName;

    @Column(unique = true)
    private String email;

    private String phoneNumber;
    private String houseNumber;
    private String street;
    private String shehia;
    private String district;
    private String region;
    private LocalDate dateOfBirth;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private UserStatus status;

    private String idDocumentUrl;
    private String proofOfResidenceUrl;

    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
