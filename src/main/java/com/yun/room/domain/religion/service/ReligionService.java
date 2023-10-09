package com.yun.room.domain.religion.service;

import com.yun.room.domain.religion.entity.Religion;
import com.yun.room.domain.religion.repository.ReligionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReligionService {
    private final ReligionRepository religionRepository;

    @Transactional
    public Religion getReligionById(Long religionId) {
        return religionRepository.findById(religionId).orElseThrow(() -> new IllegalArgumentException("해당 종교가 없습니다."));
    }
}
