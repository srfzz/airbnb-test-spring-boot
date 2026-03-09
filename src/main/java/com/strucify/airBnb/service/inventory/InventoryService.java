package com.strucify.airBnb.service.inventory;

import com.strucify.airBnb.entity.Room;

public interface InventoryService {

    void initializeRoomsForAYear(Room room);
    void initializeInventoryIfMissing(Room room);


    void deleteFutureInventory(Long roomId);
}
