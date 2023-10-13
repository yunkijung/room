package com.yun.room.domain.offer_r.service;

import com.yun.room.domain.offer_r.entity.OfferR;
import com.yun.room.domain.offer_r.repository.OfferRRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OfferRService {
    private final OfferRRepository offerRRepository;

    @Transactional
    public List<OfferR> findAllByIdIn(List<Long> ids) {
        return offerRRepository.findAllByIdIn(ids);
    }
}
