package com.yun.room.domain.member_info.repository;

import com.yun.room.domain.member_info.entity.MemberInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberInfoRepository extends JpaRepository<MemberInfo, Long> {
}
