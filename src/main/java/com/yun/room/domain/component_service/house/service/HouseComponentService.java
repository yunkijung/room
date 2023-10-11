package com.yun.room.domain.component_service.house.service;

import com.yun.room.domain.house.entity.House;
import com.yun.room.domain.house.service.HouseService;
import com.yun.room.domain.image.entity.Image;
import com.yun.room.domain.image.service.ImageService;
import com.yun.room.domain.member.entity.Member;
import com.yun.room.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HouseComponentService {
    private final HouseService houseService;
    private final MemberService memberService;
    private final ImageService imageService;

    @Transactional
    public House createHouse(House house, Long hostId, List<Image> images) {
        Member host = memberService.findById(hostId);
        house.updateHost(host);
        house.updateImages(images);
        return houseService.save(house);
    }
}
