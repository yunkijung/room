package com.yun.room.domain.component_service.member.service;

import com.yun.room.domain.component_service.member.dto.MemberInfoOptionsDto;
import com.yun.room.domain.gender.service.GenderService;
import com.yun.room.domain.house.entity.House;
import com.yun.room.domain.house.service.HouseService;
import com.yun.room.domain.member.entity.Member;
import com.yun.room.domain.member.service.MemberService;
import com.yun.room.domain.member_house_like.entity.MemberHouseLike;
import com.yun.room.domain.member_house_like.service.MemberHouseLikeService;
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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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

    private final HouseService houseService;

    private final MemberHouseLikeService memberHouseLikeService;

    @Transactional
    public Member signUp(Member member, MemberInfo memberInfo, MemberInfoOptionsDto memberInfoOptionsDto) {
        memberInfo.updateOptions(
                nationalityService.findById(memberInfoOptionsDto.getNationalityId())
                , genderService.findById(memberInfoOptionsDto.getGenderId())
                , raceService.findById(memberInfoOptionsDto.getRaceId())
                , occupationService.findById(memberInfoOptionsDto.getOccupationId())
                , religionService.findById(memberInfoOptionsDto.getReligionId())
        );

        member.updateMemberInfo(memberInfo);
        Role role = roleService.findByName("ROLE_USER");
        member.addRole(role);

        memberInfoService.save(member.getMemberInfo());

        return memberService.save(member);

    }

    @Transactional
    public void likeHouse(Long memberId, Long houseId) {

        Member member = memberService.findById(memberId);
        House house = houseService.findById(houseId);

        MemberHouseLike memberHouseLike = new MemberHouseLike(house, member);

        member.getLikeHouses().add(memberHouseLike);

    }

    @Transactional
    public void dislikeHouse(Long memberId, Long houseId) {

        Member member = memberService.findById(memberId);


        List<MemberHouseLike> likeHouses = member.getLikeHouses();

        for (Iterator<MemberHouseLike> iterator = likeHouses.iterator(); iterator.hasNext();) {
            MemberHouseLike like = iterator.next();
            if (like.getHouse().getId() == houseId) {
                iterator.remove();
            }
        }

        member.updateLikeHouses(likeHouses);
    }

    @Transactional
    public List<House> getLikeHousesByMemberId(Long memberId) {
        List<MemberHouseLike> likeHouses = memberHouseLikeService.findByMemberId(memberId);
        List<House> houses = new ArrayList<>();
        for (MemberHouseLike likeHouse : likeHouses) {
            houses.add(likeHouse.getHouse());
        }
        return houses;

    }
}
