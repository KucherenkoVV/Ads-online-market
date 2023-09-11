package ru.skypro.homework.controller;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockPart;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.ads.AdDto;
import ru.skypro.homework.dto.ads.CreateOrUpdateAdDto;
import ru.skypro.homework.dto.ads.ExtendedAdDto;
import ru.skypro.homework.dto.ads.ListAdsDto;
import ru.skypro.homework.dto.auth.Role;
import ru.skypro.homework.model.Ads;
import ru.skypro.homework.model.User;
import ru.skypro.homework.repository.AdsRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.impl.AdsServiceImpl;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class AdsControllerTest {

    @Autowired
    private AdsController adsController;

    @MockBean
    private AdsServiceImpl adsServiceImpl;

    @Autowired
    private AdsRepository adsRepository;

    @Autowired
    private UserRepository userRepository;
    private User user;
    private Ads ads;
    private ListAdsDto listAdsDto;
    private ExtendedAdDto extendedAdDto;
    private CreateOrUpdateAdDto createOrUpdateAdDto;

    @BeforeEach
    void init(){
        user  = new User();
        ads = new Ads();
        listAdsDto = new ListAdsDto();
        listAdsDto.setCount(3);
        listAdsDto.setResults(new ArrayList<>());

        extendedAdDto = new ExtendedAdDto();

        createOrUpdateAdDto = new CreateOrUpdateAdDto();
        createOrUpdateAdDto.setDescription("description");
        createOrUpdateAdDto.setPrice(1);
        createOrUpdateAdDto.setTitle("Title");
    }

    @AfterEach
    void clean() {
        userRepository.delete(user);
        adsRepository.delete(ads);
    }

    @Test
    void shouldGetAllAds() throws Exception {
        ListAdsDto listAdsDto = new ListAdsDto();
        listAdsDto.setCount(3);
        listAdsDto.setResults(new ArrayList<>());
        when(adsServiceImpl.getAllAds()).thenReturn(listAdsDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/ads");
        MockMvcBuilders.standaloneSetup(adsController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"));
    }

    @Test
    void shouldGetAdsMe() throws Exception {
        ListAdsDto listAdsDto = new ListAdsDto();
        listAdsDto.setCount(3);
        listAdsDto.setResults(new ArrayList<>());
        when(adsServiceImpl.getAdsMe(Mockito.<Authentication>any())).thenReturn(listAdsDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/ads/me");
        MockMvcBuilders.standaloneSetup(adsController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"));
    }

    @Test
    void shouldAddAds() throws Exception {

        when(adsServiceImpl.getAllAds()).thenReturn(listAdsDto);
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/ads");
        MockHttpServletRequestBuilder paramResult = getResult.param("ads", String.valueOf(new AdDto()));
        MockHttpServletRequestBuilder requestBuilder = paramResult.param("image", String.valueOf((Object) null));
        MockMvcBuilders.standaloneSetup(adsController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"));
    }

    @Test
    void shouldGetFullAd() throws Exception {

        when(adsServiceImpl.getAdFromId(Mockito.<Integer>any())).thenReturn(extendedAdDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/ads/{id}", 1);
        MockMvcBuilders.standaloneSetup(adsController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"));
    }


    @Test
    void shouldRemoveAd() throws Exception {
        doNothing().when(adsServiceImpl).removeAdById(Mockito.<Integer>any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/ads/{id}", 1);
        MockMvcBuilders.standaloneSetup(adsController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void shouldUpdateAds() throws Exception {

        when(adsServiceImpl.updateAd(Mockito.<Integer>any(), Mockito.<CreateOrUpdateAdDto>any()))
                .thenReturn(createOrUpdateAdDto);

        String content = (new ObjectMapper()).writeValueAsString(createOrUpdateAdDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.patch("/ads/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(adsController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"));
    }

    @Test
    void shouldUpdateAdsImage() throws Exception {
        MockHttpServletRequestBuilder patchResult = MockMvcRequestBuilders.patch("/ads/{id}/image", 1);
        MockHttpServletRequestBuilder requestBuilder = patchResult.param("multipartFile", String.valueOf((Object) null));
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(adsController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(415));
    }
}