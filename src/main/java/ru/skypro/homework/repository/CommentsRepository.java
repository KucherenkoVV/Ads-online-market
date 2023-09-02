package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import ru.skypro.homework.model.Comment;

import java.util.List;
import java.util.Optional;

@Component
public interface CommentsRepository extends JpaRepository<Comment, Integer> {

    Comment findCommentByAdsIdAndId(Integer adsId, Integer commentId);
    List<Comment> findAllByAdsId(Integer id);
}
