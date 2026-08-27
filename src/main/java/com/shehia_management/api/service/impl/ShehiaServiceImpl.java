package com.shehia_management.api.service.impl;



import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.shehia_management.api.dto.IssueReportRequest;
import com.shehia_management.api.dto.LetterApplicationRequest;
import com.shehia_management.api.entity.*;
import com.shehia_management.api.enums.*;
import com.shehia_management.api.repository.*;
import com.shehia_management.api.service.ShehiaService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Transactional
public class ShehiaServiceImpl implements ShehiaService {

    private final UserRepository userRepository;
    private final LetterApplicationRepository letterRepository;
    private final IssueReportRepository issueRepository;
    private final AnnouncementRepository announcementRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User registerResident(User user) {
        // Hash the password before persisting
        if (user.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        user.setRole(Role.ROLE_RESIDENT);
        user.setStatus(UserStatus.PENDING);
        return userRepository.save(user);
    }

    @Override
    public User verifyResident(Long id, UserStatus status) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Resident not found"));
        user.setStatus(status);
        return userRepository.save(user);
    }

    @Override
    public User getUserByZanId(String zanId) {
        return userRepository.findByZanId(zanId)
                .orElseThrow(() -> new RuntimeException("Resident with ZanID " + zanId + " not found"));
    }

    @Override
    public List<User> getAllResidents(UserStatus status) {
        return status != null ? userRepository.findByStatus(status) : userRepository.findAll();
    }

    @Override
    public LetterApplication applyForLetter(LetterApplicationRequest request) {
        User resident = userRepository.findById(request.getResidentId())
                .orElseThrow(() -> new RuntimeException("Resident not found"));

        String refNumber = "REF-" + (1000 + new Random().nextInt(9000));

        LetterApplication application = LetterApplication.builder()
                .referenceNumber(refNumber)
                .resident(resident)
                .letterType(request.getType())
                .dynamicFormData(request.getFormData())
                .supportingDocUrl(request.getDocUrl())
                .status(LetterStatus.PENDING)
                .build();

        return letterRepository.save(application);
    }

    @Override
    public LetterApplication reviewLetter(String refNo, LetterStatus status, String adminComments) {
        LetterApplication letter = letterRepository.findByReferenceNumber(refNo)
                .orElseThrow(() -> new RuntimeException("Letter record not found"));

        letter.setStatus(status);
        letter.setAdminComments(adminComments);
        letter.setReviewedAt(LocalDateTime.now());
        if (status == LetterStatus.APPROVED || status == LetterStatus.SIGNED) {
            letter.setGeneratedDocumentUrl("/docs/generated/" + refNo + ".pdf");
        }

        return letterRepository.save(letter);
    }

    @Override
    public List<LetterApplication> getResidentLetters(Long residentId) {
        return letterRepository.findByResidentId(residentId);
    }

    @Override
    public List<LetterApplication> getAllLetters(LetterStatus status) {
        return status != null ? letterRepository.findByStatus(status) : letterRepository.findAll();
    }

    @Override
    public IssueReport reportIssue(IssueReportRequest request) {
        User resident = userRepository.findById(request.getResidentId())
                .orElseThrow(() -> new RuntimeException("Resident not found"));

        String repNumber = "REP-" + String.format("%03d", new Random().nextInt(1000));

        IssueReport issue = IssueReport.builder()
                .reportNumber(repNumber)
                .resident(resident)
                .category(request.getCategory())
                .priority(request.getPriority())
                .location(request.getLocation())
                .description(request.getDescription())
                .photoUrl(request.getPhotoUrl())
                .status(IssueStatus.PENDING)
                .build();

        return issueRepository.save(issue);
    }

    @Override
    public IssueReport updateIssueStatus(String repNo, IssueStatus status, String assignedOfficer) {
        IssueReport issue = issueRepository.findByReportNumber(repNo)
                .orElseThrow(() -> new RuntimeException("Issue not found"));

        issue.setStatus(status);
        if (assignedOfficer != null) {
            issue.setAssignedOfficer(assignedOfficer);
        }

        return issueRepository.save(issue);
    }

    @Override
    public List<IssueReport> getResidentIssues(Long residentId) {
        return issueRepository.findByResidentId(residentId);
    }

    @Override
    public List<IssueReport> getAllIssues(IssueStatus status) {
        return status != null ? issueRepository.findByStatus(status) : issueRepository.findAll();
    }

    @Override
    public Announcement createAnnouncement(Announcement announcement) {
        if (announcement.getStatus() == AnnouncementStatus.PUBLISHED) {
            announcement.setPublishedAt(LocalDateTime.now());
        }
        return announcementRepository.save(announcement);
    }

    @Override
    public List<Announcement> getPublishedAnnouncements() {
        return announcementRepository.findByStatus(AnnouncementStatus.PUBLISHED);
    }
}
