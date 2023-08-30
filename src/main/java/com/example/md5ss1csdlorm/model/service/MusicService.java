package com.example.md5ss1csdlorm.model.service;

import com.example.md5ss1csdlorm.model.dto.MusicDtoForm;
import com.example.md5ss1csdlorm.model.entity.Music;
import com.example.md5ss1csdlorm.model.repository.IMusicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class MusicService implements IMusicService {
    private String pathUpload = "C:\\Users\\Asus\\MD4\\MD5-ss1-CSDL-ORM\\src\\main\\webapp\\WEB-INF\\upload\\";
    private String pathImage = "C:\\Users\\Asus\\MD4\\MD5-ss1-CSDL-ORM\\src\\main\\webapp\\WEB-INF\\upload\\";

    @Autowired
    private IMusicRepository iMusicRepository;

    @Override
    public List<Music> findAll() {
        return iMusicRepository.findAll();
    }

    @Override
    public Music findById(Long id) {
        return iMusicRepository.findById(id);
    }

    @Override
    public void save(MusicDtoForm m) {
        // Xử lý chuyển đổi và lưu trữ file ảnh

        // Kiểm tra xem có ảnh đại diện được tải lên hay không
        String filename = null;
        if (!(m.getLink().isEmpty())) {
            // Lấy tên file ảnh
            filename = m.getLink().getOriginalFilename();
            try {
                // Lưu ảnh vào đường dẫn được chỉ định (pathImage + filename)
                FileCopyUtils.copy(m.getLink().getBytes(), new File(pathUpload + filename));
            } catch (IOException e) {
                // Xử lý lỗi nếu có vấn đề khi lưu ảnh
                throw new RuntimeException(e);
            }
        }

        // Tạo một đối tượng Person từ thông tin trong PersonDtoForm
        Music music = new Music(
                m.getId(), m.getNameSong(), m.getSinger(),
                 m.getCategory(),filename);
        // Lưu đối tượng Person vào cơ sở dữ liệu thông qua repository
        iMusicRepository.save(music);
    }

    @Override
    public void delete(Long id) {
        iMusicRepository.delete(id);
    }
}
