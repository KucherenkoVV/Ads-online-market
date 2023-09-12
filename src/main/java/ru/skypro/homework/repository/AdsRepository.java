package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import ru.skypro.homework.model.Ads;

import java.util.List;

@Component
public interface AdsRepository extends JpaRepository<Ads, Integer> {

    @Query(value = "SELECT a FROM Ads WHERE a.title LIKE %title%", nativeQuery = true)
    List<Ads> findByTitleLike(String title);

    List<Ads> findAllByAuthorUsername(String username);
}
