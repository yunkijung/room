package com.yun.room.domain.gender.service;

import com.yun.room.domain.gender.entity.Gender;
import com.yun.room.domain.gender.repository.GenderRepository;
import com.yun.room.domain.religion.entity.Religion;
import com.yun.room.domain.religion.repository.ReligionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GenderService {
    private final GenderRepository genderRepository;

    @Transactional
    public Gender findById(Long genderId) {
        return genderRepository.findById(genderId).orElseThrow(() -> new IllegalArgumentException("해당 성별이 없습니다."));
    }
}
