package ru.skypro.homework.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.exception.IncorrectTypeOfFileException;
import ru.skypro.homework.exception.UploadFileException;
import ru.skypro.homework.service.ImageService;

import java.io.*;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
public class ImageServiceImpl implements ImageService {

    //    private final String filePath ="${upload.path}";
    private final String filePath = "C:\\Users\\JavaLearn\\IdeaProjects\\Ads-online-market\\src\\main\\resources\\img\\";
    private final String REGEX = ".+\\.(jpg|jpeg|png)";


    @Override
    public void uploadImage(MultipartFile file) {
        log.info("Starting upload image.");
        String[] split = Objects.requireNonNull(file.getOriginalFilename()).split("\\.");
        String end = UUID.randomUUID() + "." + split[split.length -1];
        File bufferFile = new File(filePath + end);
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
