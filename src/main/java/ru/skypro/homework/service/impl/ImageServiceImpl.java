package ru.skypro.homework.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.exception.IncorrectTypeOfFileException;
import ru.skypro.homework.exception.UploadFileException;
import ru.skypro.homework.service.ImageService;

import java.io.IOException;
import java.nio.file.Paths;

@Slf4j
@Service
public class ImageServiceImpl implements ImageService {

    private final String filePath ="${upload.path}";
    private final String REGEX = ".+\\.(jpg|jpeg|png)";


    @Override
    public void uploadImage(MultipartFile file) {
        log.info("Uploading image from file.");
       if(file.getName().matches(REGEX)) {
           try {
               file.transferTo(Paths.get(filePath));
               log.info("File uploaded.");
           } catch (IOException e) {
               throw new UploadFileException("Can't save uploaded file");
           }
       } else {
           throw new IncorrectTypeOfFileException("Incorrect type of file.");
       }
    }
}
