package com.example.application.data.controller;


import com.example.application.data.services.DBFiller;
import com.example.application.data.services.MigrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class ApiController {
    private final DBFiller dbFiller;
    private final MigrationService migrationService;
    @PostMapping("/filldatabase")
    public void fillDatabase() {
        dbFiller.fillDB();
    }

    @PostMapping("/migratedatabase")
    public void migrateDatabase() {
        migrationService.migrateToNoSQL();
    }

    @RequestMapping("/api/version")
    public Long apiVersion()   {
        return 1L;
    }
}