package com.yun.room.domain.role.entity;

import com.yun.room.domain.common.auditor.AuditorEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="role")
@NoArgsConstructor
@Setter
@Getter
public class Role extends AuditorEntity {
    @Id
    @Column(name="role_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20)
    private String name;

    @Override
    public String toString() {
        return "Role{" +
                "roleId=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}