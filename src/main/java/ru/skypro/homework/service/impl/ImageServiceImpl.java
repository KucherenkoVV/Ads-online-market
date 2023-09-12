package ru.skypro.homework.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.model.Ads;
import ru.skypro.homework.model.User;
import ru.skypro.homework.service.ImageService;

import java.io.*;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
public class ImageServiceImpl implements ImageService {

    @Value("${upload.path}")
    private String path;
    @Value("${upload.ads.path}")
    private String imagePath;
    @Value("${upload.users.path}")
    private String avatarPath;
    private final String REGEX = ".+\\.(jpg|jpeg|png)";


    @Override
    public void uploadImage(Ads ads, MultipartFile file) {
        log.info("Starting upload image.");

        String[] split = Objects.requireNonNull(file.getOriginalFilename()).split("\\.");
        String end = imagePath + UUID.randomUUID() + "." + split[split.length - 1];
        ads.setImage(end);
        uploadFile(file, end);
    }

    @Override
    public void uploadAvatar(User user, MultipartFile file) {
        log.info("Starting upload image.");

        String[] split = Objects.requireNonNull(file.getOriginalFilename()).split("\\.");
        String end = avatarPath + UUID.randomUUID() + "." + split[split.length - 1];
        user.setImage(end);
        uploadFile(file, end);
    }

    private void uploadFile(MultipartFile file, String end) {
        File bufferFile = new File(path + end);

        if (file.getOriginalFilename().matches(REGEX)) {

            try (BufferedInputStream bis = new BufferedInputStream(file.getInputStream());
                 FileOutputStream fos = new FileOutputStream(bufferFile);
                 BufferedOutputStream bos = new BufferedOutputStream(fos)) {
                byte[] buffer = new byte[1024];

                while (bis.read(buffer) > 0) {
                    bos.write(buffer);
                }

                log.info("Image uploaded successful.");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }


}
