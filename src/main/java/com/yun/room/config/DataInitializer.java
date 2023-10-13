package com.yun.room.config;


import com.yun.room.domain.gender.entity.Gender;
import com.yun.room.domain.gender.repository.GenderRepository;
import com.yun.room.domain.nationality.entity.Nationality;
import com.yun.room.domain.nationality.repository.NationalityRepository;
import com.yun.room.domain.occupation.entity.Occupation;
import com.yun.room.domain.occupation.repository.OccupationRepository;
import com.yun.room.domain.offer_h.entity.OfferH;
import com.yun.room.domain.offer_h.repository.OfferHRepository;
import com.yun.room.domain.offer_r.entity.OfferR;
import com.yun.room.domain.offer_r.repository.OfferRRepository;
import com.yun.room.domain.race.entity.Race;
import com.yun.room.domain.race.repository.RaceRepository;
import com.yun.room.domain.religion.entity.Religion;
import com.yun.room.domain.religion.repository.ReligionRepository;
import com.yun.room.domain.role.entity.Role;
import com.yun.room.domain.role.repository.RoleRepository;
import com.yun.room.domain.rule_h.entity.RuleH;
import com.yun.room.domain.rule_h.repository.RuleHRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initData(
            RoleRepository roleRepository
            , GenderRepository genderRepository
            , NationalityRepository nationalityRepository
            , OccupationRepository occupationRepository
            , RaceRepository raceRepository
            , ReligionRepository religionRepository
            , RuleHRepository ruleHRepository
            , OfferHRepository offerHRepository
            , OfferRRepository offerRRepository
    ) {
        return args -> {
            if (roleRepository.count() == 0) { // role 테이블에 데이터가 없을 경우
                Role userRole = new Role();
//                userRole.setId(1L);
                userRole.setName("ROLE_USER");

                Role adminRole = new Role();
//                adminRole.setRoleId(2L);
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
            if(ruleHRepository.count() == 0) {
                RuleH ruleH1 = new RuleH("", "Please, Make less noisy after 10pm.");
                RuleH ruleH2 = new RuleH("", "Extra costs for car parking ( Apartment )");
                RuleH ruleH3 = new RuleH("", "2 weeks notice, please.");
                RuleH ruleH4 = new RuleH("", "No entry for heavy smoker.");
                RuleH ruleH5 = new RuleH("", "Sorry. No allowed Pets.");
                RuleH ruleH6 = new RuleH("", "Must be wash the stuff after using.");
                ruleHRepository.save(ruleH1);
                ruleHRepository.save(ruleH2);
                ruleHRepository.save(ruleH3);
                ruleHRepository.save(ruleH4);
                ruleHRepository.save(ruleH5);
                ruleHRepository.save(ruleH6);
            }
            if(offerHRepository.count() == 0) {
                OfferH offerH1 = new OfferH("", "Wi-Fi");
                OfferH offerH2 = new OfferH("", "Free parking");
                OfferH offerH3 = new OfferH("", "swimming pool");
                OfferH offerH4 = new OfferH("", "TV");
                OfferH offerH5 = new OfferH("", "smoke alarm");
                OfferH offerH6 = new OfferH("", "Refrigerator");
                OfferH offerH7 = new OfferH("", "Balcony");
                OfferH offerH8 = new OfferH("", "Air conditioner");
                OfferH offerH9 = new OfferH("", "Washing machine");
                OfferH offerH10 = new OfferH("", "Gym");
                OfferH offerH11 = new OfferH("", "Heater");
                OfferH offerH12 = new OfferH("", "dryer");
                offerHRepository.save(offerH1);
                offerHRepository.save(offerH2);
                offerHRepository.save(offerH3);
                offerHRepository.save(offerH4);
                offerHRepository.save(offerH5);
                offerHRepository.save(offerH6);
                offerHRepository.save(offerH7);
                offerHRepository.save(offerH8);
                offerHRepository.save(offerH9);
                offerHRepository.save(offerH10);
                offerHRepository.save(offerH11);
                offerHRepository.save(offerH12);

            }
            if (offerRRepository.count() == 0) {
                OfferR offerR1 = new OfferR("", "Bed");
                OfferR offerR2 = new OfferR("", "Closet");
                OfferR offerR3 = new OfferR("", "Desk");
                OfferR offerR4 = new OfferR("", "Chair");
                OfferR offerR5 = new OfferR("", "Window");
                OfferR offerR6 = new OfferR("", "Lamp");
                OfferR offerR7 = new OfferR("", "Air conditioner");
                OfferR offerR8 = new OfferR("", "Washroom");
                OfferR offerR9 = new OfferR("", "Quilt");
                OfferR offerR10 = new OfferR("", "Pillow");
                offerRRepository.save(offerR1);
                offerRRepository.save(offerR2);
                offerRRepository.save(offerR3);
                offerRRepository.save(offerR4);
                offerRRepository.save(offerR5);
                offerRRepository.save(offerR6);
                offerRRepository.save(offerR7);
                offerRRepository.save(offerR8);
                offerRRepository.save(offerR9);
                offerRRepository.save(offerR10);
            }
        };
    }
}
