package com.strucify.airBnb.controller.inventory;

import com.strucify.airBnb.dto.inventory.InventoryDto;
import com.strucify.airBnb.dto.inventory.UpdateInventoryRequestDto;
import com.strucify.airBnb.service.inventory.InventoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/inventory")
public class InventoryController {
    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping("/room/{roomId}")
    public ResponseEntity<List<InventoryDto>> getAllInventoryByRoom(@PathVariable Long roomId) {
        return ResponseEntity.ok().body(inventoryService.getAllInventoryByRoom(roomId));
    }

    @PatchMapping("/rooms/{roomId}")
    public ResponseEntity<Void> updateInventory(@PathVariable Long roomId, @RequestBody UpdateInventoryRequestDto updateInventoryRequestDto) {
        inventoryService.updateInventory(roomId, updateInventoryRequestDto);
        return ResponseEntity.ok().build();
    }
}
