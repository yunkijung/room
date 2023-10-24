package com.yun.room.domain.member_house_like.repository;

import com.yun.room.domain.member_house_like.entity.MemberHouseLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberHouseLikeRepository extends JpaRepository<MemberHouseLike, Long> {
    List<MemberHouseLike> findByMember_Id(Long id);
}
