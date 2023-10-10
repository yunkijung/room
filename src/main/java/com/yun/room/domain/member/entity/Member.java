package com.yun.room.domain.member.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yun.room.domain.common.auditor.AuditorEntity;
import com.yun.room.domain.house.entity.House;
import com.yun.room.domain.member_info.entity.MemberInfo;
import com.yun.room.domain.role.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity // Database Table과 맵핑하는 객체.
@Table(name="member") // Database 테이블 이름 user3 와 User라는 객체가 맵핑.
@NoArgsConstructor // 기본생성자가 필요하다.
@Getter
public class Member extends AuditorEntity {
    @Id // 이 필드가 Table의 PK.
    @Column(name="member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY) // memberId는 자동으로 생성되도록 한다. 1,2,3,4
    private Long memberId;

    @Column(length = 255, unique = true)
    private String email;

    @JsonIgnore
    @Column(length = 500)
    private String password;

    @Column(length = 50)
    private String name;

    @Column
    private Boolean isLocked;

    @OneToOne
    @JoinColumn(name = "member_info_id")
    private MemberInfo memberInfo;


    @ManyToMany
    @JoinTable(name = "member_role",
            joinColumns = @JoinColumn(name = "member_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    @OneToMany(mappedBy = "host")
    private List<House> houses;

    public Member(String email, String password, String name, Boolean isLocked) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.isLocked = isLocked;
    }

    @Override
    public String toString() {
        return "User{" +
                "memberId=" + memberId +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public void addRole(Role role) {
        roles.add(role);
    }

    public void updateMemberInfo(MemberInfo memberInfo) {
        this.memberInfo = memberInfo;
        memberInfo.updateMember(this);
    }

}

// User -----> Role