package com.yun.room.api.member.dto.member_info;

import com.yun.room.domain.gender.entity.Gender;
import com.yun.room.domain.member.entity.Member;
import com.yun.room.domain.nationality.entity.Nationality;
import com.yun.room.domain.occupation.entity.Occupation;
import com.yun.room.domain.race.entity.Race;
import com.yun.room.domain.religion.entity.Religion;
import com.yun.room.domain.role.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
public class MemberInfoDto {
    private String email;
    private String name;
    private Long memberId;
    private List<String> roles = new ArrayList<>();
    private LocalDate birth;
    private GenderDto gender;
    private ReligionDto religion;
    private OccupationDto occupation;
    private RaceDto race;
    private NationalityDto nationality;

    public MemberInfoDto(Member member) {
        this.email = member.getEmail();
        this.name = member.getName();
        this.memberId = member.getId();

        Set<Role> roles = member.getRoles();
        for (Role role : roles) {
            addRole(role.getName());
        }

        this.birth = member.getMemberInfo().getBirth();
        this.gender = new GenderDto(member.getMemberInfo().getGender());
        this.religion = new ReligionDto(member.getMemberInfo().getReligion());
        this.occupation = new OccupationDto(member.getMemberInfo().getOccupation());
        this.race = new RaceDto(member.getMemberInfo().getRace());
        this.nationality = new NationalityDto(member.getMemberInfo().getNationality());
    }

    public void addRole(String role){
        roles.add(role);
    }
}

@Data
@AllArgsConstructor
class GenderDto {
    private Long genderId;
    private String type;

    public GenderDto(Gender gender) {
        this.genderId = gender.getId();
        this.type = gender.getType();
    }
}
@Data
@AllArgsConstructor
class OccupationDto {
    private Long occupationId;
    private String name;
    public OccupationDto(Occupation occupation) {
        this.occupationId = occupation.getId();
        this.name = occupation.getName();
    }
}
@Data
@AllArgsConstructor
class ReligionDto {
    private Long religionId;
    private String name;
    public ReligionDto(Religion religion) {
        this.religionId = religion.getId();
        this.name = religion.getName();
    }
}
@Data
@AllArgsConstructor
class RaceDto {
    private Long raceId;
    private String type;
    public RaceDto(Race race) {
        this.raceId = race.getId();
        this.type = race.getType();
    }
}
@Data
@AllArgsConstructor
class NationalityDto {
    private Long nationalityId;
    private String name;
    public NationalityDto(Nationality nationality) {
        this.nationalityId = nationality.getId();
        this.name = nationality.getName();
    }
}
