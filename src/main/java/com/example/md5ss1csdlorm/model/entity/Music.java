package com.example.md5ss1csdlorm.model.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Music {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nameSong;
    private String singer;
    private String category;
    private String link;

    public Music() {
    }

    public Music(Long id, String nameSong, String singer, String category, String link) {
        this.id = id;
        this.nameSong = nameSong;
        this.singer = singer;
        this.category = category;
        this.link = link;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameSong() {
        return nameSong;
    }

    public void setNameSong(String nameSong) {
        this.nameSong = nameSong;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
    public void copy(Music m){
        this.nameSong = m.getNameSong();
        this.singer = m.getSinger();
        this.category = m.getCategory();
        this.link = m.getLink();
    }
}
