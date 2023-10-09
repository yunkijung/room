package com.yun.room.config;


import com.yun.room.domain.gender.entity.Gender;
import com.yun.room.domain.gender.repository.GenderRepository;
import com.yun.room.domain.nationality.entity.Nationality;
import com.yun.room.domain.nationality.repository.NationalityRepository;
import com.yun.room.domain.occupation.entity.Occupation;
import com.yun.room.domain.occupation.repository.OccupationRepository;
import com.yun.room.domain.race.entity.Race;
import com.yun.room.domain.race.repository.RaceRepository;
import com.yun.room.domain.religion.entity.Religion;
import com.yun.room.domain.religion.repository.ReligionRepository;
import com.yun.room.domain.role.entity.Role;
import com.yun.room.domain.role.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RoleInitializer {

    @Bean
    public CommandLineRunner initRoles(
            RoleRepository roleRepository
            , GenderRepository genderRepository
            , NationalityRepository nationalityRepository
            , OccupationRepository occupationRepository
            , RaceRepository raceRepository
            , ReligionRepository religionRepository
    ) {
        return args -> {
            if (roleRepository.count() == 0) { // role 테이블에 데이터가 없을 경우
                Role userRole = new Role();
                userRole.setRoleId(1L);
                userRole.setName("ROLE_USER");

                Role adminRole = new Role();
                adminRole.setRoleId(2L);
                adminRole.setName("ROLE_ADMIN");

                roleRepository.save(userRole);
                roleRepository.save(adminRole);
            }

            if (genderRepository.count() == 0) {
                Gender genderMale = new Gender("M");
                genderRepository.save(genderMale);
                Gender genderFemale = new Gender("F");
                genderRepository.save(genderFemale);
            }

            if (nationalityRepository.count() == 0) {
                Nationality nationality1 = new Nationality("KOR");
                nationalityRepository.save(nationality1);
                Nationality nationality2 = new Nationality("CA");
                nationalityRepository.save(nationality2);
            }
            if (occupationRepository.count() == 0) {
                Occupation occupation1 = new Occupation("IT");
                occupationRepository.save(occupation1);
                Occupation occupation2 = new Occupation("DESIGN");
                occupationRepository.save(occupation2);
            }
            if (raceRepository.count() == 0) {
                Race race1 = new Race("ASIAN");
                raceRepository.save(race1);
                Race race2 = new Race("BLACK");
                raceRepository.save(race2);
            }
            if (religionRepository.count() == 0) {
                Religion religion1 = new Religion("CHRISTIAN");
                religionRepository.save(religion1);
                Religion religion2 = new Religion("MUSLIM");
                religionRepository.save(religion2);
            }
        };
    }
}
