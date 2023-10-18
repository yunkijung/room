package com.yun.room.api.house.dto.get_all_houses;

import com.yun.room.domain.image.entity.Image;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class ImageDto {
    private Long imageId;
    private String originFilename;
    private String fileUrl;

    public ImageDto(Image image) {
        this.imageId = image.getId();
        this.originFilename = image.getOriginFilename();
        this.fileUrl = image.getFileUrl();
    }
}
