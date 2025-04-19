package hf25_16.debugging_chickens.mental_health_backend.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.concurrent.CompletableFuture;

public interface ImageStorageService {
    CompletableFuture<String> uploadImage(MultipartFile image) throws Exception;
    CompletableFuture<Void> deleteImage(String imageUrl) throws Exception;
}