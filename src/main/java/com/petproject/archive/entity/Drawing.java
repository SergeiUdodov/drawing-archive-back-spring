package com.petproject.archive.entity;

import jakarta.persistence.*;

import java.util.Objects;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Drawing{" +
                "id=" + id +
                ", designation='" + designation + '\'' +
                ", name='" + name + '\'' +
                ", version='" + version + '\'' +
                ", date='" + date + '\'' +
                ", imageURL='" + imageURL + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Drawing drawing)) return false;
        return Objects.equals(getId(), drawing.getId()) && Objects.equals(getDesignation(), drawing.getDesignation()) && Objects.equals(getName(),
                drawing.getName()) && Objects.equals(getVersion(), drawing.getVersion()) && Objects.equals(getDate(),
                drawing.getDate()) && Objects.equals(getImageURL(), drawing.getImageURL());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getDesignation(), getName(), getVersion(), getDate(), getImageURL());
    }
}
