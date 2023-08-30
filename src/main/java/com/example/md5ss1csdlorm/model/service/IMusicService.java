package com.example.md5ss1csdlorm.model.service;

import com.example.md5ss1csdlorm.model.dto.MusicDtoForm;
import com.example.md5ss1csdlorm.model.entity.Music;

import java.util.List;

public interface IMusicService {
    List<Music> findAll();
    Music findById(Long id);
    void save(MusicDtoForm m);
    void delete(Long id);
}
