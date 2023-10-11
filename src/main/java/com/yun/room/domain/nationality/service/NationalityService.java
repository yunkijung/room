package com.yun.room.domain.nationality.service;

import com.yun.room.domain.nationality.entity.Nationality;
import com.yun.room.domain.nationality.repository.NationalityRepository;
import com.yun.room.domain.race.entity.Race;
import com.yun.room.domain.race.repository.RaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class NationalityService {
    private final NationalityRepository nationalityRepository;

    @Transactional
    public Nationality findById(Long nationalityId) {
        return nationalityRepository.findById(nationalityId).orElseThrow(() -> new IllegalArgumentException("해당 국가가 없습니다."));
    }
}
