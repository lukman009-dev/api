package com.shehia_management.api.service;

import com.shehia_management.api.dto.IssueReportRequest;
import com.shehia_management.api.dto.LetterApplicationRequest;
import com.shehia_management.api.entity.Announcement;
import com.shehia_management.api.entity.IssueReport;
import com.shehia_management.api.entity.LetterApplication;
import com.shehia_management.api.entity.User;
import com.shehia_management.api.enums.*;

import java.util.List;

public interface ShehiaService {
    // User Management
    User registerResident(User user);
    User verifyResident(Long id, UserStatus status);
    User getUserByZanId(String zanId);
    List<User> getAllResidents(UserStatus status);

    // Letter Management
    LetterApplication applyForLetter(LetterApplicationRequest request);
    LetterApplication reviewLetter(String refNo, LetterStatus status, String adminComments);
    List<LetterApplication> getResidentLetters(Long residentId);
    List<LetterApplication> getAllLetters(LetterStatus status);

    // Issue Management
    IssueReport reportIssue(IssueReportRequest request);
    IssueReport updateIssueStatus(String repNo, IssueStatus status, String assignedOfficer);
    List<IssueReport> getResidentIssues(Long residentId);
    List<IssueReport> getAllIssues(IssueStatus status);

    // Announcement Management
    Announcement createAnnouncement(Announcement announcement);
    List<Announcement> getPublishedAnnouncements();
}
