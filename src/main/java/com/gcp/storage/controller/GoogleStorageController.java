package com.gcp.storage.controller;

import com.gcp.storage.service.GoogleStorageService;
import com.google.cloud.storage.Blob;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class GoogleStorageController {

    private final GoogleStorageService googleStorageService;

    @GetMapping("/download")
    public String download() {
        return googleStorageService.download().toString();
    }

    @GetMapping("/upload")
    public ResponseEntity<List<Blob>> upload() {
        return new ResponseEntity<>(googleStorageService.upload(), HttpStatus.OK);
    }
}
