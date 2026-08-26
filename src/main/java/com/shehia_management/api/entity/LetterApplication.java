package com.shehia_management.api.entity;

import jakarta.persistence.*;
import lombok.*;
import com.shehia_management.api.enums.LetterStatus;
import com.shehia_management.api.enums.LetterType;

import java.time.LocalDateTime;

@Entity
@Table(name = "letter_applications")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LetterApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String referenceNumber; // e.g. REF-2026-991

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resident_id", nullable = false)
    private User resident;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LetterType letterType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LetterStatus status;

    @Column(columnDefinition = "TEXT")
    private String dynamicFormData;

    private String supportingDocUrl;
    private String generatedDocumentUrl;

    @Column(columnDefinition = "TEXT")
    private String adminComments;

    private LocalDateTime submittedAt;
    private LocalDateTime reviewedAt;

    @PrePersist
    protected void onCreate() {
        this.submittedAt = LocalDateTime.now();
    }
}
