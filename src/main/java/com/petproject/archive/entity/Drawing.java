package com.petproject.archive.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Objects;

@Data
@Entity
@Table(name = "drawing", schema = "public")
public class Drawing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "designation")
    private String designation;

    @Column(name = "name")
    private String name;

    @Column(name = "version")
    private long version;

    @Column(name = "date")
    private String date;

    @Column(name = "imageURL")
    private String imageURL;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "drawing_users", joinColumns = @JoinColumn(name = "drawing_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    private User user;

    public Drawing() {
    }

    public Drawing(String designation, String name, long version, String date, String imageURL) {
        this.designation = designation;
        this.name = name;
        this.version = version;
        this.date = date;
        this.imageURL = imageURL;
    }

}
