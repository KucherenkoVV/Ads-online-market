package ru.skypro.homework.service;

import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.model.Ads;
import ru.skypro.homework.model.User;

public interface ImageService {

    void uploadImage(Ads ads, MultipartFile file);

    void uploadAvatar(User user, MultipartFile file);
}
