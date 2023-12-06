package com.photosharesite.backend.filemanipulation;

import com.google.inject.name.Named;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import javax.inject.Inject;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.CompleteMultipartUploadRequest;
import software.amazon.awssdk.services.s3.model.CompleteMultipartUploadResponse;
import software.amazon.awssdk.services.s3.model.CompletedMultipartUpload;
import software.amazon.awssdk.services.s3.model.CompletedPart;
import software.amazon.awssdk.services.s3.model.CreateMultipartUploadRequest;
import software.amazon.awssdk.services.s3.model.CreateMultipartUploadResponse;
import software.amazon.awssdk.services.s3.model.UploadPartRequest;

public class FileUploader {

  private final S3Client s3Client;
  private final String BUCKET_NAME;

  private static final int PART_SIZE = 5 * 1024 * 1024;

  @Inject
  public FileUploader(S3Client s3Client, @Named("BUCKET_NAME") String BUCKET_NAME) {
    this.s3Client = s3Client;
    this.BUCKET_NAME = BUCKET_NAME;
  }

  public String UploadFileMultipart(String keyName, InputStream inputStream) throws IOException {
    // First create a multipart upload and get the upload id
    CreateMultipartUploadRequest createMultipartUploadRequest =
        CreateMultipartUploadRequest.builder().bucket(BUCKET_NAME).key(keyName).build();

    CreateMultipartUploadResponse initResponse =
        s3Client.createMultipartUpload(createMultipartUploadRequest);
    String uploadId = initResponse.uploadId();

    // Create a list of completable futures
    List<CompletableFuture<CompletedPart>> futures = new ArrayList<>();

    int partNumber = 1; // part numbers must start at 1
    int readBytes;
    byte[] buffer = new byte[PART_SIZE];
    BufferedInputStream bufferedStream = new BufferedInputStream(inputStream, PART_SIZE);
    while (true) {
      readBytes = bufferedStream.readNBytes(buffer, 0, PART_SIZE);
      if (readBytes == 0) break;

      // Create the request to upload a part.
      UploadPartRequest uploadPartRequest =
          UploadPartRequest.builder()
              .bucket(BUCKET_NAME)
              .key(keyName)
              .uploadId(uploadId)
              .partNumber(partNumber)
              .build();

      int currentPartNumber = partNumber;
      byte[] BytesToUpload = Arrays.copyOfRange(buffer, 0, readBytes);
      CompletableFuture<CompletedPart> future =
          CompletableFuture.supplyAsync(
                  () ->
                      s3Client
                          .uploadPart(uploadPartRequest, RequestBody.fromBytes(BytesToUpload))
                          .eTag())
              .thenApply(
                  eTag -> CompletedPart.builder().partNumber(currentPartNumber).eTag(eTag).build());

      futures.add(future);
      partNumber++;
    }

    List<CompletedPart> completedParts =
        futures.stream().map(CompletableFuture::join).collect(Collectors.toList());

    // Finally call completeMultipartUpload operation to tell S3 to merge all uploaded
    // parts and finish the multipart operation.
    CompletedMultipartUpload completedMultipartUpload =
        CompletedMultipartUpload.builder().parts(completedParts).build();

    CompleteMultipartUploadRequest completeMultipartUploadRequest =
        CompleteMultipartUploadRequest.builder()
            .bucket(BUCKET_NAME)
            .key(keyName)
            .uploadId(uploadId)
            .multipartUpload(completedMultipartUpload)
            .build();

    CompleteMultipartUploadResponse response =
        s3Client.completeMultipartUpload(completeMultipartUploadRequest);

    return response.location();
  }
}
