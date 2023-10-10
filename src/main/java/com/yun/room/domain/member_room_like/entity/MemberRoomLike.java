package com.yun.room.domain.member_room_like.entity;

import com.yun.room.domain.member.entity.Member;
import com.yun.room.domain.room.entity.Room;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class MemberRoomLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberRoomLikeId;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
}
