package com.strucify.airBnb.service.inventory;

import com.strucify.airBnb.dto.HotelSearch.HotelSearchRequestDto;
import com.strucify.airBnb.dto.hotelDto.Hoteldto;
import com.strucify.airBnb.entity.Hotel;
import com.strucify.airBnb.entity.Inventory;
import com.strucify.airBnb.entity.Room;
import com.strucify.airBnb.repository.InventoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class InventoryServiceImpl implements InventoryService {
    private final InventoryRepository inventoryRepository;
    private final ModelMapper modelMapper;

    public InventoryServiceImpl(InventoryRepository inventoryRepository, ModelMapper modelMapper) {
        this.inventoryRepository = inventoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public void initializeRoomsForAYear(Room room) {
        LocalDate today = LocalDate.now();
        LocalDate enddate = today.plusYears(1);
        for (; today.isAfter(enddate); today.plusDays(1)) {
            Inventory inventory = Inventory.builder()
                    .hotel(room.getHotel())
                    .room(room)
                    .bookedCount(0)
                    .city(room.getHotel().getCity())
                    .surgeFactor(BigDecimal.ONE)
                    .totalCount(room.getTotalCount())
                    .closed(false)
                    .build();
            inventoryRepository.save(inventory);
        }


    }

    @Override
    @Transactional
    public void initializeInventoryIfMissing(Room room) {
        LocalDate today = LocalDate.now();
        LocalDate endDate = today.plusYears(1);
        Optional<Inventory> lastInventory = inventoryRepository
                .findFirstByRoomOrderByDateDesc(room);
        LocalDate startDate = today;
        if (lastInventory.isPresent()) {
            startDate = lastInventory.get().getDate().plusDays(1);
        }
        if (startDate.isAfter(endDate)) {
            log.info("Inventory already exists for room {}", room.getId());
            return;
        }
        List<Inventory> newInventory = new ArrayList<>();
        while (startDate.isBefore(endDate) || startDate.isEqual(endDate)) {
            Inventory inv = Inventory.builder()
                    .hotel(room.getHotel())
                    .room(room)
                    .date(startDate)
                    .bookedCount(0)
                    .city(room.getHotel().getCity())
                    .surgeFactor(BigDecimal.ONE)
                    .totalCount(room.getTotalCount())
                    .price(BigDecimal.ZERO)
                    .closed(false)
                    .build();
            newInventory.add(inv);
            startDate = startDate.plusDays(1);
        }

        inventoryRepository.saveAll(newInventory);
    }

    @Override
    @Transactional
    public void deleteFutureInventory(Long roomId) {

        inventoryRepository.deleteFutureInventory(roomId, LocalDate.now());

    }

    @Override
    @Transactional(readOnly = true)
    public Page<Hoteldto> searchHotels(HotelSearchRequestDto hotelSearchRequestDto) {
        Long daysCount = ChronoUnit.DAYS.between(hotelSearchRequestDto.getStartDate(), hotelSearchRequestDto.getEndDate());
        Pageable pageable = PageRequest.of(hotelSearchRequestDto.getPage(), hotelSearchRequestDto.getSize());
        Page<Hotel> hotelpage = inventoryRepository.findAvailableHotels(hotelSearchRequestDto.getCity(), hotelSearchRequestDto.getRoomCount(), hotelSearchRequestDto.getStartDate(), hotelSearchRequestDto.getEndDate(), daysCount, pageable);
        return hotelpage.map(hotel -> modelMapper.map(hotel, Hoteldto.class));
    }
}
