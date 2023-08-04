package ru.skypro.homework.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Instant;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "comment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull
    private Instant createdAt;
    @NotNull
    private String text;
    @ManyToOne(fetch = FetchType.LAZY)
    private Ads ads;
    @ManyToOne(fetch = FetchType.LAZY)
    private User author;

}
