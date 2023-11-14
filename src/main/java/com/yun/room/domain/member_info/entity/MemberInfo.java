package com.yun.room.domain.member_info.entity;

import com.yun.room.domain.common.auditor.AuditorEntity;
import com.yun.room.domain.gender.entity.Gender;
import com.yun.room.domain.member.entity.Member;
import com.yun.room.domain.member_image.entity.MemberImage;
import com.yun.room.domain.nationality.entity.Nationality;
import com.yun.room.domain.occupation.entity.Occupation;
import com.yun.room.domain.race.entity.Race;
import com.yun.room.domain.religion.entity.Religion;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.bytebuddy.asm.Advice;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name="member_info")
@NoArgsConstructor // 기본생성자가 필요하다.
@Getter
public class MemberInfo extends AuditorEntity {

    @Id // 이 필드가 Table의 PK.
    @Column(name="member_info_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private LocalDate birth;


    @CreationTimestamp // 현재시간이 저장될 때 자동으로 생성.
    private LocalDateTime regdate;

    @OneToOne
    @JoinColumn(name = "member_image_id")
    private MemberImage memberImage;

    @ManyToOne
    @JoinColumn(name = "nationality_id")
    private Nationality nationality;

    @ManyToOne
    @JoinColumn(name = "gender_id")
    private Gender gender;

    @ManyToOne
    @JoinColumn(name = "race_id")
    private Race race;

    @ManyToOne
    @JoinColumn(name = "occupation_id")
    private Occupation occupation;

    @ManyToOne
    @JoinColumn(name = "religion_id")
    private Religion religion;

    @OneToOne(mappedBy = "memberInfo")
    private Member member;

    public MemberInfo(
            LocalDate birth
    ) {
        this.birth = birth;
    }

    public void updateOptions(
            Nationality nationality
            , Gender gender
            , Race race
            , Occupation occupation
            , Religion religion) {
        if(nationality != null) {
            this.nationality = nationality;
        }

        if(gender != null) {
            this.gender = gender;
        }

        if(race != null) {
            this.race = race;
        }

        if(occupation != null) {
            this.occupation = occupation;
        }

        if(religion != null) {
            this.religion = religion;
        }

    }

    public void updateBirth(LocalDate birth) {
        this.birth = birth;
    }

    public void updateMember(Member member) {
        this.member = member;
    }
}
