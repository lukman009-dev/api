package com.shehia_management.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.shehia_management.api.entity.IssueReport;
import com.shehia_management.api.enums.IssueStatus;

import java.util.List;
import java.util.Optional;

public interface IssueReportRepository extends JpaRepository<IssueReport, Long> {
    Optional<IssueReport> findByReportNumber(String reportNumber);
    List<IssueReport> findByResidentId(Long residentId);
    List<IssueReport> findByStatus(IssueStatus status);
}
