package com.yun.room.domain.member_house_like.service;

import com.yun.room.domain.member_house_like.entity.MemberHouseLike;
import com.yun.room.domain.member_house_like.repository.MemberHouseLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberHouseLikeService {
    private final MemberHouseLikeRepository memberHouseLikeRepository;

    @Transactional
    public MemberHouseLike save(MemberHouseLike memberHouseLike) {
        return memberHouseLikeRepository.save(memberHouseLike);
    }

    @Transactional
    public void deleteById(Long id) {
        memberHouseLikeRepository.deleteById(id);
    }

    @Transactional
    public List<MemberHouseLike> findByMemberId(Long id) {
        return memberHouseLikeRepository.findByMember_Id(id);
    }
}
