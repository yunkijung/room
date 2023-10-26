package com.yun.room.api.member.controller;


import com.yun.room.api.house.dto.get_options.OfferHDto;
import com.yun.room.api.house.dto.get_options.RuleHDto;
import com.yun.room.api.house.dto.search_houses.HouseResponseDto;
import com.yun.room.api.member.dto.like_house.LikeHouseDto;
import com.yun.room.api.member.dto.login.MemberLoginDto;
import com.yun.room.api.member.dto.login.MemberLoginResponseDto;

import com.yun.room.api.member.dto.signup.MemberSignupDto;
import com.yun.room.api.member.dto.signup.MemberSignupResponseDto;
import com.yun.room.api.room.dto.get_options.OfferRDto;
import com.yun.room.domain.component_service.member.dto.MemberInfoOptionsDto;
import com.yun.room.domain.component_service.member.service.MemberComponentService;
import com.yun.room.domain.house.entity.House;
import com.yun.room.domain.house.service.HouseService;
import com.yun.room.domain.house_offer_h.entity.HouseOfferH;
import com.yun.room.domain.house_rule_h.entity.HouseRuleH;
import com.yun.room.domain.image.entity.Image;
import com.yun.room.domain.member.entity.Member;
import com.yun.room.domain.member.service.MemberService;
import com.yun.room.domain.member_info.entity.MemberInfo;
import com.yun.room.domain.refreshtoken.entity.RefreshToken;
import com.yun.room.domain.refreshtoken.service.RefreshTokenService;
import com.yun.room.domain.religion.service.ReligionService;
import com.yun.room.domain.role.entity.Role;
import com.yun.room.domain.room.entity.Room;
import com.yun.room.domain.room_offer_r.entity.RoomOfferR;
import com.yun.room.security.jwt.util.IfLogin;
import com.yun.room.security.jwt.util.JwtTokenizer;
import com.yun.room.security.jwt.util.LoginUserDto;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Slf4j
@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/members")
public class MemberController {

    private final JwtTokenizer jwtTokenizer;
    private final MemberService memberService;
    private final RefreshTokenService refreshTokenService;
    private final PasswordEncoder passwordEncoder;

    private final MemberComponentService memberComponentService;
    private final HouseService houseService;


//    public MemberController(JwtTokenizer jwtTokenizer, MemberService memberService, RefreshTokenService refreshTokenService, PasswordEncoder passwordEncoder) {
//        this.jwtTokenizer = jwtTokenizer;
//        this.memberService = memberService;
//        this.refreshTokenService = refreshTokenService;
//        this.passwordEncoder = passwordEncoder;
//    }

    @PostMapping("/signup")
    public ResponseEntity signup(@RequestBody @Valid MemberSignupDto memberSignupDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        Member member = new Member(memberSignupDto.getEmail()
                , passwordEncoder.encode(memberSignupDto.getPassword())
                , memberSignupDto.getName()
                , Boolean.FALSE);


        MemberInfo memberInfo = new MemberInfo(memberSignupDto.getBirth());


        MemberInfoOptionsDto memberInfoOptionsDto = new MemberInfoOptionsDto(
                memberSignupDto.getGenderId(),
                memberSignupDto.getOccupationId(),
                memberSignupDto.getReligionId(),
                memberSignupDto.getRaceId(),
                memberSignupDto.getNationalityId()
        );


        Member savedMember = memberComponentService.signUp(member, memberInfo, memberInfoOptionsDto);

        MemberSignupResponseDto memberSignupResponse = new MemberSignupResponseDto();
        memberSignupResponse.setMemberId(savedMember.getId());
        memberSignupResponse.setName(savedMember.getName());
        memberSignupResponse.setRegdate(savedMember.getMemberInfo().getRegdate());
        memberSignupResponse.setEmail(savedMember.getEmail());

        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("member", memberSignupResponse);
        // 회원가입
        return new ResponseEntity(resultMap, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid MemberLoginDto loginDto, BindingResult bindingResult, HttpServletResponse response) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        // email이 없을 경우 Exception이 발생한다. Global Exception에 대한 처리가 필요하다.
        Member member = memberService.findByEmail(loginDto.getEmail());
        if(!passwordEncoder.matches(loginDto.getPassword(), member.getPassword())){
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        // List<Role> ===> List<String>
        List<String> roles = member.getRoles().stream().map(Role::getName).collect(Collectors.toList());

        // JWT토큰을 생성하였다. jwt라이브러리를 이용하여 생성.
        String accessToken = jwtTokenizer.createAccessToken(member.getId(), member.getEmail(), member.getName(), roles);
        String refreshToken = jwtTokenizer.createRefreshToken(member.getId(), member.getEmail(), member.getName(), roles);

        // RefreshToken을 DB에 저장한다. 성능 때문에 DB가 아니라 Redis에 저장하는 것이 좋다.
        RefreshToken refreshTokenEntity = new RefreshToken();
        refreshTokenEntity.setValue(refreshToken);
        refreshTokenEntity.setMemberId(member.getId());
        refreshTokenService.addRefreshToken(refreshTokenEntity);

        // create a cookie
        Cookie cookie = new Cookie("refreshToken", refreshToken);

        // expires in 7 days
        cookie.setMaxAge(7 * 24 * 60 * 60);

        // optional properties
//        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        cookie.setPath("/");

        // add cookie to response
        response.addCookie(cookie);

        MemberLoginResponseDto loginResponse = MemberLoginResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken("httoOnly")
                .memberId(member.getId())
                .nickname(member.getName())
                .build();

        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("member", loginResponse);

        return new ResponseEntity(resultMap, HttpStatus.OK);
    }

    @DeleteMapping("/logout")
    public ResponseEntity logout(@CookieValue(name = "refreshToken", required = true) String token, HttpServletResponse response) {
        refreshTokenService.deleteRefreshToken(token);
        // create a cookie
        Cookie cookie = new Cookie("refreshToken", "");

        // expires in 7 days
        cookie.setMaxAge(0);

        // optional properties
//        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        cookie.setPath("/");

        // add cookie to response
        response.addCookie(cookie);

        return new ResponseEntity(HttpStatus.OK);
    }

    /*
    1. 전달받은 유저의 아이디로 유저가 존재하는지 확인한다.
    2. RefreshToken이 유효한지 체크한다.
    3. AccessToken을 발급하여 기존 RefreshToken과 함께 응답한다.
     */
    @PostMapping("/refreshToken")
    public ResponseEntity requestRefresh(@CookieValue(name = "refreshToken", required = true) String token) {
        RefreshToken refreshToken = refreshTokenService.findRefreshToken(token).orElseThrow(() -> new IllegalArgumentException("Refresh token not found"));
        Claims claims = jwtTokenizer.parseRefreshToken(refreshToken.getValue());

        Long memberId = Long.valueOf((Integer)claims.get("memberId"));

        Member member = memberService.findById(memberId);


        List roles = (List) claims.get("roles");
        String email = claims.getSubject();

        String accessToken = jwtTokenizer.createAccessToken(memberId, email, member.getName(), roles);

        MemberLoginResponseDto loginResponse = MemberLoginResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken("httpOnly")
                .memberId(member.getId())
                .nickname(member.getName())
                .build();

        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("member", loginResponse);

        return new ResponseEntity(resultMap, HttpStatus.OK);
    }

    @GetMapping("/info")
    public ResponseEntity userinfo(@IfLogin LoginUserDto loginUserDto) {
        Member member = memberService.findByEmail(loginUserDto.getEmail());
        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("member", loginUserDto);

        return new ResponseEntity(resultMap, HttpStatus.OK);
    }


    @GetMapping("/check/email")
    public ResponseEntity checkEmail(@RequestParam String email) {
        Optional<Member> findMember = memberService.checkByEmail(email);
        Boolean result;
        if(findMember.isEmpty()) {
            result = false;
        } else {
            result = true;
        }
        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("exist", result);

        return new ResponseEntity(resultMap, HttpStatus.OK);
    }

    @GetMapping("/check/name")
    public ResponseEntity checkName(@RequestParam String name) {
        Optional<Member> findMember = memberService.checkByName(name);
        Boolean result;
        if(findMember.isEmpty()) {
            result = false;
        } else {
            result = true;
        }
        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("exist", result);

        return new ResponseEntity(resultMap, HttpStatus.OK);
    }

    @GetMapping("/houses/business")
    public ResponseEntity getMyHouses(@IfLogin LoginUserDto loginUserDto) {
        Long memberId = loginUserDto.getMemberId();

        List<House> houses = houseService.findAllByHost(memberId);

        List<HouseResponseDto> responseDtos = new ArrayList<>();

        //House data transfer
        for (House house : houses) {
            responseDtos.add(new HouseResponseDto(house));
        }

        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("houseList", responseDtos);

        return new ResponseEntity(resultMap, HttpStatus.OK);
    }

    @PostMapping("/houses/like")
    public ResponseEntity likeHouse(@IfLogin LoginUserDto loginUserDto, @RequestBody @Valid LikeHouseDto likeHouseDto) {
        memberComponentService.likeHouse(loginUserDto.getMemberId(), likeHouseDto.getHouseId());
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping("/houses/like")
    public ResponseEntity dislikeHouse(@IfLogin LoginUserDto loginUserDto, @RequestBody @Valid LikeHouseDto likeHouseDto) {
        memberComponentService.dislikeHouse(loginUserDto.getMemberId(), likeHouseDto.getHouseId());
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/houses/like")
    public ResponseEntity getAllLikeHouses(@IfLogin LoginUserDto loginUserDto) {

        List<House> houses = memberComponentService.getLikeHousesByMemberId(loginUserDto.getMemberId());
        List<HouseResponseDto> responseDtos = new ArrayList<>();

        //House data transfer
        for (House house : houses) {
            responseDtos.add(new HouseResponseDto(house));
        }

        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("houseList", responseDtos);

        return new ResponseEntity(resultMap, HttpStatus.OK);

    }
}
