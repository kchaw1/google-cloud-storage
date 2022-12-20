package com.gcp.storage.service;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class GoogleStorageService {

    private final Storage storage;
    private static final String BUCKET_NAME = "beenz-test-bucket";

    private void setup() {
    }

    public List<Blob> download() {
        long beforeTime = System.currentTimeMillis(); //코드 실행 전에 시간 받아오기

        List<BlobId> blobIds = new ArrayList<>();
        for (int i = 0; i < 600; i++) {
            final String fileName = "test" + i + ".jpg";
            BlobId blobId = BlobId.of(BUCKET_NAME, fileName);
            blobIds.add(blobId);
        }

        /**
         * 파일 600개 기준
         * 1. batch get : 11814 ms
         * 2. 병렬 스트림 : 11609 ms
         * 3. 일반 스트림 : 85622 ms
         */
//        List<Blob> blobs = storage.get(blobIds);
        List<Blob> blobs = blobIds.stream().parallel().map(storage::get).toList();
//        List<Blob> blobs = blobIds.stream().map(storage::get).toList();

        long afterTime = System.currentTimeMillis(); // 코드 실행 후에 시간 받아오기
        long secDiffTime = (afterTime - beforeTime); //두 시간에 차 계산
        log.info("걸린시간 : {} ms", secDiffTime);
        return blobs;
    }

    public List<Blob> upload() {
        long beforeTime = System.currentTimeMillis(); //코드 실행 전에 시간 받아오기

        List<Blob> list = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream("download/download.jpg")) {
            byte[] content = fis.readAllBytes();

            for (int i = 0; i < 600; i++) {
                String fileName = "test" + i + ".jpg";
                BlobId blobId = BlobId.of(BUCKET_NAME, fileName);
                BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
                        .setContentType("image/jpeg")
                        .build();
                Blob blob = storage.create(blobInfo, content);

                list.add(blob);
            }

        } catch (IOException e) {
            log.error(e.getMessage());
        }

        long afterTime = System.currentTimeMillis(); // 코드 실행 후에 시간 받아오기
        long secDiffTime = (afterTime - beforeTime); //두 시간에 차 계산
        log.info("걸린시간 : {} ms", secDiffTime);

        return list;
    }
}
