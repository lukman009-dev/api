package com.shehia_management.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.shehia_management.api.entity.Announcement;
import com.shehia_management.api.entity.IssueReport;
import com.shehia_management.api.entity.LetterApplication;
import com.shehia_management.api.entity.User;
import com.shehia_management.api.enums.IssueStatus;
import com.shehia_management.api.enums.LetterStatus;
import com.shehia_management.api.enums.UserStatus;
import com.shehia_management.api.service.ShehiaService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {

    private final ShehiaService shehiaService;

    // Resident Management
    @GetMapping("/residents")
    public ResponseEntity<List<User>> getAllResidents(@RequestParam(required = false) UserStatus status) {
        return ResponseEntity.ok(shehiaService.getAllResidents(status));
    }

    @PutMapping("/residents/{id}/verify")
    public ResponseEntity<User> verifyResident(@PathVariable Long id, @RequestParam UserStatus status) {
        return ResponseEntity.ok(shehiaService.verifyResident(id, status));
    }

    // Letter Applications
    @GetMapping("/letters")
    public ResponseEntity<List<LetterApplication>> getAllLetters(@RequestParam(required = false) LetterStatus status) {
        return ResponseEntity.ok(shehiaService.getAllLetters(status));
    }

    @PutMapping("/letters/{refNo}/review")
    public ResponseEntity<LetterApplication> reviewLetter(
            @PathVariable String refNo,
            @RequestParam LetterStatus status,
            @RequestParam(required = false) String adminComments) {
        return ResponseEntity.ok(shehiaService.reviewLetter(refNo, status, adminComments));
    }

    // Issue Resolution
    @GetMapping("/issues")
    public ResponseEntity<List<IssueReport>> getAllIssues(@RequestParam(required = false) IssueStatus status) {
        return ResponseEntity.ok(shehiaService.getAllIssues(status));
    }

    @PutMapping("/issues/{repNo}/status")
    public ResponseEntity<IssueReport> updateIssueStatus(
            @PathVariable String repNo,
            @RequestParam IssueStatus status,
            @RequestParam(required = false) String officer) {
        return ResponseEntity.ok(shehiaService.updateIssueStatus(repNo, status, officer));
    }

    // Announcements
    @PostMapping("/announcements")
    public ResponseEntity<Announcement> createAnnouncement(@RequestBody Announcement announcement) {
        return ResponseEntity.ok(shehiaService.createAnnouncement(announcement));
    }
}
