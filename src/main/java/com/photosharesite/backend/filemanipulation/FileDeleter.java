package com.photosharesite.backend.filemanipulation;

import software.amazon.awssdk.services.s3.S3Client;

public class FileDeleter {
    private final S3Client s3Client;
    private final String BUCKET_NAME;

    public FileDeleter(S3Client s3Client, String BUCKET_NAME) {
        this.s3Client = s3Client;
        this.BUCKET_NAME = BUCKET_NAME;
    }

    public boolean DeleteFile(String fileName){
        //todo: fill in
        return true;
    }
}
