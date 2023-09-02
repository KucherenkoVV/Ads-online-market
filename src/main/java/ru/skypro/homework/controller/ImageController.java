package ru.skypro.homework.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@RestController
@RequestMapping("/images")
@CrossOrigin(value = "http://localhost:3000")
public class ImageController {

    @Value("${upload.ads.path}")
    private String imagePath;
    @Value("${upload.users.path}")
    private String avatarPath;

    @GetMapping("/users/{imageId}")
    public byte[] getUserImage (@PathVariable String imageId) throws IOException {
        return Files.readAllBytes(Path.of(avatarPath + File.separator + imageId));
    }

    @GetMapping("/ads/{imageId}")
    public byte[] getAdsImage (@PathVariable String imageId) throws IOException {
        return Files.readAllBytes(Path.of(imagePath + File.separator + imageId));
    }
}
