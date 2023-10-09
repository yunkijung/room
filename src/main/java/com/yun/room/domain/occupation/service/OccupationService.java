package com.yun.room.domain.occupation.service;

import com.yun.room.domain.occupation.entity.Occupation;
import com.yun.room.domain.occupation.repository.OccupationRepository;
import com.yun.room.domain.race.entity.Race;
import com.yun.room.domain.race.repository.RaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OccupationService {
    private final OccupationRepository occupationRepository;

    @Transactional
    public Occupation getOccupationById(Long occupationId) {
        return occupationRepository.findById(occupationId).orElseThrow(() -> new IllegalArgumentException("해당 직종이 없습니다."));
    }
}
