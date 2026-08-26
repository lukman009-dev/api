package com.shehia_management.api.entity;

import jakarta.persistence.*;
import lombok.*;
import com.shehia_management.api.enums.AnnouncementStatus;
import com.shehia_management.api.enums.AnnouncementType;
import com.shehia_management.api.enums.PriorityLevel;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "announcements")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Announcement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Enumerated(EnumType.STRING)
    private AnnouncementType type;

    @Enumerated(EnumType.STRING)
    private PriorityLevel priority;

    @Enumerated(EnumType.STRING)
    private AnnouncementStatus status;

    private String targetShehia;
    private String imageUrl;
    private LocalDate expiryDate;

    private LocalDateTime publishedAt;
}
