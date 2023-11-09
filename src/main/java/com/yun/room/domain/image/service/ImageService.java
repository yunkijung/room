package com.yun.room.domain.image.service;

import com.yun.room.domain.image.entity.Image;
import com.yun.room.domain.image.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ImageService {
    private final ImageRepository imageRepository;

    @Transactional
    public Image saveImage(Image image) {
        return imageRepository.save(image);
    }
}
