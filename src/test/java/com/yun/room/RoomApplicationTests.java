package com.yun.room;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yun.room.domain.house.entity.House;
import com.yun.room.domain.house.entity.QHouse;
import com.yun.room.domain.role.entity.QRole;
import com.yun.room.domain.role.entity.Role;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class RoomApplicationTests {

    @Autowired
    EntityManager em;
	@Test
	void contextLoads() {
        Role role = new Role();
        em.persist(role);

        JPAQueryFactory query = new JPAQueryFactory(em);

        QRole qRole = QRole.role;

        Role result = query
                .selectFrom(qRole)
                .fetchOne();

        assertThat(result).isEqualTo(role);
        assertThat(result.getId()).isEqualTo(role.getId());
    }

}
