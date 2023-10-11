package com.yun.room.domain.image.service;

import com.yun.room.domain.image.entity.Image;
import com.yun.room.domain.image.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ImageService {
    private final ImageRepository imageRepository;

    public Image saveImage(Image image) {
        return imageRepository.save(image);
    }
}
