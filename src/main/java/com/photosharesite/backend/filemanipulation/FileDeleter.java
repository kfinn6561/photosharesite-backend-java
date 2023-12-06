package com.photosharesite.backend.filemanipulation;

import com.google.inject.Inject;
import com.photosharesite.backend.PhotoShareSiteConfiguration;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;

public class FileDeleter {
  private final S3Client s3Client;
  private final String BUCKET_NAME;

  @Inject
  public FileDeleter(S3Client s3Client, PhotoShareSiteConfiguration configuration) {
    this.s3Client = s3Client;
    this.BUCKET_NAME = configuration.getMediaFilesBucketName();
  }

  public void DeleteFile(String fileName) {
    s3Client.deleteObject(DeleteObjectRequest.builder().bucket(BUCKET_NAME).key(fileName).build());
  }
}
