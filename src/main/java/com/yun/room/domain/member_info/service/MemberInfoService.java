package com.yun.room.domain.member_info.service;

import com.yun.room.domain.member_info.entity.MemberInfo;
import com.yun.room.domain.member_info.repository.MemberInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberInfoService {

    private final MemberInfoRepository memberInfoRepository;

    @Transactional
    public MemberInfo saveMemberInfo(MemberInfo memberInfo) {
        return memberInfoRepository.save(memberInfo);
    }

}
