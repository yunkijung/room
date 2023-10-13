package com.yun.room.domain.room.service;

import com.yun.room.domain.room.entity.Room;
import com.yun.room.domain.room.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RoomService {
    private final RoomRepository roomRepository;

    @Transactional
    public Room save(Room room) {
        return roomRepository.save(room);
    }
}
