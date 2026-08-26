package com.shehia_management.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.shehia_management.api.entity.Announcement;
import com.shehia_management.api.enums.AnnouncementStatus;

import java.util.List;

public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {
    List<Announcement> findByStatus(AnnouncementStatus status);
    List<Announcement> findByTargetShehiaOrTargetShehiaIsNull(String targetShehia);
}
