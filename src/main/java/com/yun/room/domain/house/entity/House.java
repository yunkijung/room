package com.yun.room.domain.house.entity;

import com.yun.room.domain.common.auditor.AuditorEntity;
import com.yun.room.domain.common.embedded.Address;
import com.yun.room.domain.house_offer_h.entity.HouseOfferH;
import com.yun.room.domain.house_rule_h.entity.HouseRuleH;
import com.yun.room.domain.image.entity.Image;
import com.yun.room.domain.member.entity.Member;
import com.yun.room.domain.room.entity.Room;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.locationtech.jts.geom.Point;


import javax.persistence.*;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
public class House extends AuditorEntity {

    @Id
    @Column(name="house_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private String type;

    @Embedded
    private Address address;

    @Column(columnDefinition = "POINT SRID 4326")
    private Point point;

    private Integer roomCount;
    private Integer washroomCount;
    private Integer toiletCount;
    private Integer kitchenCount;
    private Integer livingRoomCount;

    @OneToMany(mappedBy = "house", cascade = CascadeType.ALL, orphanRemoval=true)
    private List<HouseOfferH> houseOfferHList;

    @OneToMany(mappedBy = "house", cascade = CascadeType.ALL, orphanRemoval=true)
    private List<HouseRuleH> houseRuleHList;

    @ManyToOne
    @JoinColumn(name = "host_id")
    private Member host;

    @OneToMany(mappedBy = "house")
    private List<Room> rooms;

    @OneToMany(mappedBy = "house", cascade = CascadeType.ALL, orphanRemoval=true)
    private List<Image> images;

    public House(
            String title
            , String description
            , String type
            , Address address
            , Point point
            , Integer roomCount
            , Integer washroomCount
            , Integer toiletCount
            , Integer kitchenCount
            , Integer livingRoomCount) {
        this.title = title;
        this.description = description;
        this.type = type;
        this.address = address;
        this.point = point;
        this.roomCount = roomCount;
        this.washroomCount = washroomCount;
        this.toiletCount = toiletCount;
        this.kitchenCount = kitchenCount;
        this.livingRoomCount = livingRoomCount;
    }

    public void updatePoint(Point point) {
        this.point = point;
    }

    public void updateHost(Member host) {
        this.host = host;
        host.getHouses().add(this);
    }

    public void updateImages(List<Image> images) {
        this.images = images;
        for (Image image : images) {
            if(image.getHouse() != null) {
                continue;
            }
            image.updateHouse(this);
        }
    }

    public void updateHouseInfo(
            String title
            , String description
            , String type
            , Address address
            , Point point
            , Integer roomCount
            , Integer washroomCount
            , Integer toiletCount
            , Integer kitchenCount
            , Integer livingRoomCount
    ) {
        if(title != null) {
            this.title = title;
        }
        if(description != null) {
            this.description = description;
        }
        if(type != null) {
            this.type = type;
        }
        if(address != null) {
            this.address = address;
        }
        if(point != null) {
            this.point = point;
        }
        if(roomCount != null) {
            this.roomCount = roomCount;
        }
        if(washroomCount != null) {
            this.washroomCount = washroomCount;
        }
        if(toiletCount != null) {
            this.toiletCount = toiletCount;
        }
        if(kitchenCount != null) {
            this.kitchenCount = kitchenCount;
        }
        if(livingRoomCount != null) {
            this.livingRoomCount = livingRoomCount;
        }
    }

    public void updateHouseOfferHList(List<HouseOfferH> houseOfferHList) {
        this.houseOfferHList = houseOfferHList;
    }
    public void updateHouseRuleHList(List<HouseRuleH> houseRuleHList) {
        this.houseRuleHList = houseRuleHList;
    }
}
