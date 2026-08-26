package com.shehia_management.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.shehia_management.api.entity.IssueReport;
import com.shehia_management.api.entity.LetterApplication;
import com.shehia_management.api.entity.User;
import com.shehia_management.api.enums.IssueCategory;
import com.shehia_management.api.enums.IssuePriority;
import com.shehia_management.api.enums.LetterType;
import com.shehia_management.api.service.ShehiaService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/resident")
@RequiredArgsConstructor
public class ResidentController {

    private final ShehiaService shehiaService;

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) {
        return ResponseEntity.ok(shehiaService.registerResident(user));
    }

    @GetMapping("/profile/{zanId}")
    public ResponseEntity<User> getProfile(@PathVariable String zanId) {
        return ResponseEntity.ok(shehiaService.getUserByZanId(zanId));
    }

    @PostMapping("/letters/apply")
    public ResponseEntity<LetterApplication> applyForLetter(
            @RequestParam Long residentId,
            @RequestParam LetterType type,
            @RequestParam String formData,
            @RequestParam(required = false) String docUrl) {
        return ResponseEntity.ok(shehiaService.applyForLetter(residentId, type, formData, docUrl));
    }

    @GetMapping("/letters/{residentId}")
    public ResponseEntity<List<LetterApplication>> getMyLetters(@PathVariable Long residentId) {
        return ResponseEntity.ok(shehiaService.getResidentLetters(residentId));
    }

    @PostMapping("/issues/report")
    public ResponseEntity<IssueReport> reportIssue(
            @RequestParam Long residentId,
            @RequestParam IssueCategory category,
            @RequestParam IssuePriority priority,
            @RequestParam String location,
            @RequestParam String description,
            @RequestParam(required = false) String photoUrl) {
        return ResponseEntity.ok(shehiaService.reportIssue(residentId, category, priority, location, description, photoUrl));
    }

    @GetMapping("/issues/{residentId}")
    public ResponseEntity<List<IssueReport>> getMyIssues(@PathVariable Long residentId) {
        return ResponseEntity.ok(shehiaService.getResidentIssues(residentId));
    }
}
