package ru.skypro.homework.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@RequiredArgsConstructor
@Table(name = "ads")
public class Ads {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @NotNull
    @Column(name = "title")
    private String title;

    @Column(name ="description")
    private String description;

    @NotNull
    @Column(name = "price")
    private Integer price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private User author;

    @Column(name = "image")
    private String image;

    @JsonIgnore
    @OneToMany(mappedBy = "ads", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Comment> comments;

}
