package ru.skypro.homework.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockPart;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import ru.skypro.homework.dto.ads.CreateOrUpdateAdDto;
import ru.skypro.homework.dto.auth.Role;
import ru.skypro.homework.model.Ads;
import ru.skypro.homework.model.User;
import ru.skypro.homework.repository.AdsRepository;
import ru.skypro.homework.repository.UserRepository;

@SpringBootTest
@AutoConfigureMockMvc
class AdsControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private AdsRepository adsRepository;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private UserRepository userRepository;
    private Authentication authentication;
    private final User user = new User();
    private final CreateOrUpdateAdDto createAds = new CreateOrUpdateAdDto();
    private final Ads ads = new Ads();


    @BeforeEach
    void init() {
        user.setUsername("user@user.ru");
        user.setFirstName("Ivan");
        user.setLastName("Ivanov");
        user.setPhone("+79991112233");
        user.setPassword(encoder.encode("password"));
        user.setRole(Role.USER);
        user.setEnabled(true);
        userRepository.save(user);

        ads.setTitle("Ads");
        ads.setDescription("description");
        ads.setPrice(1000);
        ads.setAuthor(user);
        adsRepository.save(ads);
    }

    @AfterEach
    void clean() {
        userRepository.delete(user);
        adsRepository.delete(ads);
    }


    @Test
    void shouldGetAllAds() {

    }

    @Test
    void shouldGetFullAd() {

    }

    @Test
    void shouldAddAds() {

    }

    @Test
    void shouldRemoveAd() {

    }

    @Test
    void shouldUpdateAds() {

    }

    @Test
    void shouldGetAdsMe() {

    }

    @Test
    void shouldUpdateAdsImage() {

    }
}