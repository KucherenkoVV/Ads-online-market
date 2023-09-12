package ru.skypro.homework.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.skypro.homework.dto.adsComment.AdsCommentDto;
import ru.skypro.homework.dto.adsComment.CreateOrUpdateAdsCommentDto;
import ru.skypro.homework.dto.adsComment.ListAdsCommentsDto;
import ru.skypro.homework.dto.auth.Role;
import ru.skypro.homework.mapper.AdsCommentMapper;
import ru.skypro.homework.mapper.AdsMapper;
import ru.skypro.homework.model.Ads;
import ru.skypro.homework.model.Comment;
import ru.skypro.homework.model.User;
import ru.skypro.homework.repository.CommentsRepository;

@ContextConfiguration(classes = {CommentServiceImpl.class})
@ExtendWith(SpringExtension.class)
class CommentServiceImplTest {
    @MockBean
    private AdsCommentMapper adsCommentMapper;

    @MockBean
    private AdsMapper adsMapper;

    @MockBean
    private AdsServiceImpl adsServiceImpl;

    @Autowired
    private CommentServiceImpl commentServiceImpl;

    @MockBean
    private CommentsRepository commentsRepository;

    @MockBean
    private UserServiceImpl userServiceImpl;
    private AdsCommentDto adsCommentDto;
    private Comment comment;
    private Ads ads;
    private User user;
    private Comment comment2;
    private Authentication authentication;

    @BeforeEach
    void init(){
        adsCommentDto = new AdsCommentDto();
        adsCommentDto.setAuthor(1);
        adsCommentDto.setAuthorFirstName("FirstName");
        adsCommentDto.setAuthorImage("/image/users/testimage.jpg");
        adsCommentDto.setCreatedAt("2023.09.01");
        adsCommentDto.setPk(1);
        adsCommentDto.setText("Text");

        user = new User();
        user.setAds(new ArrayList<>());
        user.setComments(new ArrayList<>());
        user.setEnabled(true);
        user.setFirstName("FirstName");
        user.setId(1);
        user.setImage("/image/users/testimage.jpg");
        user.setLastName("LastName");
        user.setPassword("12345678");
        user.setPhone("+79990001122");
        user.setRole(Role.USER);
        user.setUsername("username");

        ads = new Ads();
        ads.setAuthor(user);
        ads.setComments(new ArrayList<>());
        ads.setDescription("description");
        ads.setId(1);
        ads.setImage("image/ads/testimage.jpg");
        ads.setPrice(1);
        ads.setTitle("Title");

        comment = new Comment();
        comment.setAds(ads);
        comment.setAuthor(user);
        comment.setCreatedAt(LocalDate.of(2023, 9, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
        comment.setId(1);
        comment.setText("Text1");

        comment2 = new Comment();
        comment2.setAds(ads);
        comment2.setAuthor(user);
        comment2.setCreatedAt(LocalDate.of(2023, 9, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
        comment2.setId(1);
        comment2.setText("Text2");

    }

    @AfterEach
    void remove() {
        commentsRepository.delete(comment);
        commentsRepository.delete(comment2);
    }

    @Test
    void shouldGetCommentById() {

        when(adsCommentMapper.toCommentDtoFromEntity(comment)).thenReturn(adsCommentDto);

        when(commentsRepository.findCommentByAdsIdAndId(ads.getId(), comment.getId()))
                .thenReturn(comment);
        assertSame(adsCommentDto, commentServiceImpl.getCommentById(1, 1));
        verify(adsCommentMapper).toCommentDtoFromEntity(comment);
        verify(commentsRepository).findCommentByAdsIdAndId(ads.getId(), comment.getId());
    }

    @Test
    void shouldGetAllComments() {
        ArrayList<Comment> commentList = new ArrayList<>();
        when(commentsRepository.findAllByAdsId(ads.getId())).thenReturn(commentList);
        ListAdsCommentsDto comments = commentServiceImpl.getAllComments(1);
        assertEquals(0, comments.getCount().intValue());
        assertEquals(commentList, comments.getResults());
        verify(commentsRepository).findAllByAdsId(ads.getId());
    }

    @Test
    void shouldAddCommentToAd() {

        when(adsServiceImpl.getAdsFromId(ads.getId())).thenReturn(ads);

        when(adsCommentMapper.toCommentDtoFromEntity(comment)).thenReturn(adsCommentDto);
        when(adsCommentMapper.toEntityFromCommentDto(adsCommentDto)).thenReturn(comment);

        when(userServiceImpl.getUserByUsername(comment.getAuthor().getUsername())).thenReturn(user);
        when(commentsRepository.save(comment2)).thenReturn(comment2);

        adsCommentDto.setText("text");
        assertSame(adsCommentDto,
                commentServiceImpl.addCommentToAd(1, adsCommentDto, new TestingAuthenticationToken("Test", "TestToken")));
        verify(adsServiceImpl).getAdsFromId(ads.getId());
        verify(adsCommentMapper).toCommentDtoFromEntity(comment);
        verify(adsCommentMapper).toEntityFromCommentDto(adsCommentDto);
        verify(commentsRepository).save(comment);
    }

    @Test
    void shouldRemoveCommentFromAd() {

        when(commentsRepository.findCommentByAdsIdAndId(ads.getId(), comment.getId()))
                .thenReturn(comment2);
        doNothing().when(commentsRepository).delete(comment);
        commentServiceImpl.removeCommentFromAd(1, 1);
        verify(commentsRepository).findCommentByAdsIdAndId(ads.getId(), comment.getId());
    }

}

