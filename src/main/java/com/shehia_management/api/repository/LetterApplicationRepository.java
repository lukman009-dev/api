package com.shehia_management.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.shehia_management.api.entity.LetterApplication;
import com.shehia_management.api.enums.LetterStatus;

import java.util.List;
import java.util.Optional;

public interface LetterApplicationRepository extends JpaRepository<LetterApplication, Long> {
    Optional<LetterApplication> findByReferenceNumber(String referenceNumber);
    List<LetterApplication> findByResidentId(Long residentId);
    List<LetterApplication> findByStatus(LetterStatus status);
}
