package com.gcp.storage.controller;

import com.gcp.storage.dto.DownloadDto;
import com.gcp.storage.dto.UploadDto;
import com.gcp.storage.service.GoogleStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class GoogleStorageController {

    private final GoogleStorageService googleStorageService;

    @PostMapping("/download")
    public String download(@RequestBody DownloadDto downloadDto) {
        return googleStorageService.download(downloadDto).toString();
    }

    @PostMapping("/upload")
    public String download(@RequestBody UploadDto uploadDto) throws IOException {
        return googleStorageService.upload(uploadDto).toString();
    }
}
