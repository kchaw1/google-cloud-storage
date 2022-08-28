package com.gcp.storage.service;

import com.gcp.storage.dto.DownloadDto;
import com.gcp.storage.dto.UploadDto;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
public class GoogleStorageService {

    private final Storage storage;

    public Blob download(DownloadDto downloadDto) {
        Blob blob = storage.get(downloadDto.getBucketName(), downloadDto.getDownloadFileName());
        blob.downloadTo(Paths.get(downloadDto.getLocalFileLocation()));
        return blob;
    }

    public Blob upload(UploadDto uploadDto) throws IOException {
        BlobId blobId = BlobId.of(uploadDto.getBucketName(), uploadDto.getUploadFileName());
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
                                    .setContentType("image/jpeg")
                                    .build();

        byte[] content = new FileInputStream(uploadDto.getLocalFileLocation()).readAllBytes();

        return storage.create(blobInfo, content);
    }
}
