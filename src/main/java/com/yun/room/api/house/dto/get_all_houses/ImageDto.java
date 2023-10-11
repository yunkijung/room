package com.yun.room.api.house.dto.get_all_houses;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ImageDto {
    private String originFilename;
    private String fileUrl;
}
