package com.shehia_management.api.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.shehia_management.api.entity.Announcement;
import com.shehia_management.api.service.ShehiaService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/public")
@RequiredArgsConstructor
public class PublicPortalController {

    private final ShehiaService shehiaService;

    @GetMapping("/announcements")
    public ResponseEntity<List<Announcement>> getPublicAnnouncements() {
        return ResponseEntity.ok(shehiaService.getPublishedAnnouncements());
    }
}
