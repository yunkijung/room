package com.yun.room.domain.race.service;

import com.yun.room.domain.race.entity.Race;
import com.yun.room.domain.race.repository.RaceRepository;
import com.yun.room.domain.religion.entity.Religion;
import com.yun.room.domain.religion.repository.ReligionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RaceService {
    private final RaceRepository raceRepository;

    @Transactional
    public Race getRaceById(Long raceId) {
        return raceRepository.findById(raceId).orElseThrow(() -> new IllegalArgumentException("해당 인종이 없습니다."));
    }
}
