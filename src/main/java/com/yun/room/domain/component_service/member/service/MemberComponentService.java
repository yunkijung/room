package com.yun.room.domain.component_service.member.service;

import com.yun.room.domain.component_service.member.dto.MemberInfoOptionsDto;
import com.yun.room.domain.gender.service.GenderService;
import com.yun.room.domain.member.entity.Member;
import com.yun.room.domain.member.service.MemberService;
import com.yun.room.domain.member_info.entity.MemberInfo;
import com.yun.room.domain.member_info.service.MemberInfoService;
import com.yun.room.domain.nationality.entity.Nationality;
import com.yun.room.domain.nationality.service.NationalityService;
import com.yun.room.domain.occupation.service.OccupationService;
import com.yun.room.domain.race.service.RaceService;
import com.yun.room.domain.religion.service.ReligionService;
import com.yun.room.domain.role.entity.Role;
import com.yun.room.domain.role.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberComponentService {

    private final MemberService memberService;
    private final MemberInfoService memberInfoService;

    private final RoleService roleService;
    private final ReligionService religionService;
    private final GenderService genderService;
    private final OccupationService occupationService;
    private final RaceService raceService;
    private final NationalityService nationalityService;


    @Transactional
    public Member signUp(Member member, MemberInfo memberInfo, MemberInfoOptionsDto memberInfoOptionsDto) {
        memberInfo.updateOptions(
                nationalityService.getNationalityById(memberInfoOptionsDto.getNationalityId())
                , genderService.getGenderById(memberInfoOptionsDto.getGenderId())
                , raceService.getRaceById(memberInfoOptionsDto.getRaceId())
                , occupationService.getOccupationById(memberInfoOptionsDto.getOccupationId())
                , religionService.getReligionById(memberInfoOptionsDto.getReligionId())
        );

        member.updateMemberInfo(memberInfo);
        Role role = roleService.getRoleByName("ROLE_USER");
        member.addRole(role);

        memberInfoService.saveMemberInfo(member.getMemberInfo());

        return memberService.saveMember(member);

    }
}
