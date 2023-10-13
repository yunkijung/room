package com.yun.room.domain.member_image.entity;

import com.yun.room.domain.member_info.entity.MemberInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class MemberImage {
    @Id
    @Column(name="member_image_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String originFileName;

    private String uniqueFileName;

    @OneToOne(mappedBy = "memberImage")
    private MemberInfo memberInfo;
}
