package com.gcp.storage.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UploadDto {
    private String bucketName;
    private String uploadFileName;
    private String localFileLocation;
}
