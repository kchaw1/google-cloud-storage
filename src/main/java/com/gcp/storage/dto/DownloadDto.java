package com.gcp.storage.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DownloadDto {
    private String bucketName;
    private String downloadFileName;
    private String localFileLocation;
}
