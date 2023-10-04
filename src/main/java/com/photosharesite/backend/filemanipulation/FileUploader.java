package com.photosharesite.backend.filemanipulation;

import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class FileUploader {

    private final S3Client s3Client;
    private final String BUCKET_NAME;

    private static final int PART_SIZE = 5* 1024*1024;

    public FileUploader(S3Client s3Client, String BUCKET_NAME) {
        this.s3Client = s3Client;
        this.BUCKET_NAME = BUCKET_NAME;
    }

    public void UploadFileMultipart(String keyName, InputStream inputStream) throws IOException {
        // First create a multipart upload and get the upload id
        CreateMultipartUploadRequest createMultipartUploadRequest = CreateMultipartUploadRequest.builder()
                .bucket(BUCKET_NAME)
                .key(keyName)
                .build();

        CreateMultipartUploadResponse initResponse = s3Client.createMultipartUpload(createMultipartUploadRequest);
        String uploadId = initResponse.uploadId();


        // Create a list of completable futures
        List<CompletableFuture<CompletedPart>> futures = new ArrayList<>();

        int partNumber =1; // part numbers must start at 1
        int readBytes;
        byte[] buffer = new byte[PART_SIZE];
        BufferedInputStream bufferedStream = new BufferedInputStream(inputStream, PART_SIZE);
        while (true){
            readBytes = bufferedStream.read(buffer);
            if (readBytes == -1) break;

            // Create the request to upload a part.
            UploadPartRequest uploadPartRequest = UploadPartRequest.builder()
                    .bucket(BUCKET_NAME)
                    .key(keyName)
                    .uploadId(uploadId)
                    .partNumber(partNumber).build();

//            String eTag = s3Client.uploadPart(uploadPartRequest, RequestBody.fromBytes(buffer)).eTag();
//            CompletedPart part = CompletedPart.builder().partNumber(partNumber).eTag(eTag).build();

            int currentPartNumber = partNumber;
            CompletableFuture<CompletedPart> future = CompletableFuture.supplyAsync(
                    () -> s3Client.uploadPart(uploadPartRequest, RequestBody.fromBytes(buffer)).eTag())
                            .thenApply(eTag -> CompletedPart.builder().partNumber(currentPartNumber).eTag(eTag).build());

            futures.add(future);
            partNumber++;
        }

        List<CompletedPart> completedParts
                = futures.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList());

        // Finally call completeMultipartUpload operation to tell S3 to merge all uploaded
        // parts and finish the multipart operation.
        CompletedMultipartUpload completedMultipartUpload = CompletedMultipartUpload.builder()
                .parts(completedParts)
                .build();

        CompleteMultipartUploadRequest completeMultipartUploadRequest =
                CompleteMultipartUploadRequest.builder()
                        .bucket(BUCKET_NAME)
                        .key(keyName)
                        .uploadId(uploadId)
                        .multipartUpload(completedMultipartUpload)
                        .build();

        s3Client.completeMultipartUpload(completeMultipartUploadRequest);
    }
}
