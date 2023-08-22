package ru.skypro.homework.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.exception.IncorrectTypeOfFileException;
import ru.skypro.homework.exception.UploadFileException;
import ru.skypro.homework.service.ImageService;

import java.io.IOException;
import java.nio.file.Paths;

@Service
public class ImageServiceImpl implements ImageService {

    private final String filePath ="src/main/resources/img";
    private final String REGEX = ".+\\.(jpg|jpeg|png)";


    @Override
    public void uploadImage(MultipartFile file) {
       if(file.getName().matches(REGEX)) {
           try {
               file.transferTo(Paths.get(filePath));
           } catch (IOException e) {
               throw new UploadFileException("Can't save uploaded file");
           }
       } else {
           throw new IncorrectTypeOfFileException("Incorrect type of file.");
       }
    }
}
