package com.example.md5ss1csdlorm.model.dto;

import org.springframework.web.multipart.MultipartFile;

public class MusicDtoForm {
    private Long id;
    private String nameSong;
    private String singer;
    private String category;
    private MultipartFile link;

    public MusicDtoForm() {
    }

    public MusicDtoForm(Long id, String nameSong, String singer, String category, MultipartFile link) {
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

    public MultipartFile getLink() {
        return link;
    }

    public void setLink(MultipartFile link) {
        this.link = link;
    }
}
