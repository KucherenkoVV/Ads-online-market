package ru.skypro.homework.repository;

import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import ru.skypro.homework.dto.ads.AdDto;
import ru.skypro.homework.model.Ads;

import java.util.List;

@Component
public interface AdsRepository extends JpaRepository<Ads, Integer> {

    @Query(value = "SELECT a FROM Ads WHERE a.title LIKE %title%", nativeQuery = true)
    List<Ads> findByTitleLike(String title);

    List<Ads> findAllByAuthorUsername(String username);
}
