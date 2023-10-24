package com.yun.room.domain.member_house_like.entity;

import com.yun.room.domain.house.entity.House;
import com.yun.room.domain.member.entity.Member;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class MemberHouseLike {
    @Id
    @Column(name = "member_house_like_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "house_id")
    private House house;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    public MemberHouseLike(House house, Member member) {
        this.house = house;
        this.member = member;
    }
}
