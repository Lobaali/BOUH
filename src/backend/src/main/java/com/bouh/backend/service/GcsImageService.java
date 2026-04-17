package com.bouh.backend.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

import com.google.cloud.ReadChannel;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage.SignUrlOption;
import com.google.firebase.cloud.StorageClient;

import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;

@Slf4j
@Service
public class GcsImageService {

    @Value("${gcs.bucket.name}")
    private String bucketName;

    private Bucket bucket;

    @PostConstruct
    public void init() {
        try {
            this.bucket = StorageClient.getInstance().bucket(bucketName);
            log.info("GCS bucket initialized successfully: {}", bucketName);
        } catch (Exception e) {
            log.error("Failed to initialize GCS bucket: {}", bucketName, e);
            this.bucket = null;
        }
    }

    /**
     * Generate public download URL
     */
    public String generateDownloadUrl(String imagePath) {
        ensureBucketInitialized();

        if (imagePath == null || imagePath.isBlank()) {
            throw new IllegalArgumentException("Invalid image path");
        }

        Blob blob = bucket.get(imagePath);
        if (blob == null) {
            throw new RuntimeException("Image not found: " + imagePath);
        }

        URL signedUrl = blob.signUrl(
                604800L,
                TimeUnit.SECONDS,
                SignUrlOption.withV4Signature());

        return signedUrl.toString();
    }

    /**
     * Download image (streaming)
     */
    public BufferedImage downloadImage(String imagePath) throws Exception {
        ensureBucketInitialized();

        Blob blob = bucket.get(imagePath);
        if (blob == null) {
            throw new RuntimeException("Image not found: " + imagePath);
        }

        try (ReadChannel reader = blob.reader();
             InputStream inputStream = Channels.newInputStream(reader)) {
            return ImageIO.read(inputStream);
        }
    }

    /**
     * Delete image
     */
    public void deleteImage(String imagePath) {
        ensureBucketInitialized();

        if (imagePath == null || imagePath.isBlank()) {
            log.error("Invalid image path");
            return;
        }

        Blob blob = bucket.get(imagePath);

        if (blob != null) {
            blob.delete();
            log.info("Image deleted: {}", imagePath);
        } else {
            log.warn("Image not found: {}", imagePath);
        }
    }

    private void ensureBucketInitialized() {
        if (bucket == null) {
            throw new IllegalStateException("GCS bucket is not initialized");
        }
    }
}