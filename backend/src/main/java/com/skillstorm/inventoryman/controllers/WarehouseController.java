package com.skillstorm.inventoryman.controllers;

import com.skillstorm.inventoryman.models.Warehouse;
import com.skillstorm.inventoryman.services.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/warehouses")
public class WarehouseController {
    
    @Autowired
    private WarehouseService warehouseService;

    @GetMapping // Gets all warehouses
    public List<Warehouse> getAllWarehouses() {
        return warehouseService.getAllWarehouses();
    }

    @GetMapping("/{id}") // Gets a warehouse by id
    public Warehouse getWarehouseById(@PathVariable Long id) {
        return warehouseService.getWarehouseById(id);
    }

    @PostMapping // Creates a new warehouse
    public Warehouse createWarehouse(@RequestBody Warehouse warehouse) {
        return warehouseService.saveWarehouse(warehouse);
    }

    @PutMapping("/{id}") // Updates a warehouse by id
    public Warehouse updateWarehouse(@PathVariable Long id, @RequestBody Warehouse warehouseDetails) {
        System.out.println(warehouseService.getWarehouseById(id));
        Warehouse warehouse = warehouseService.getWarehouseById(id);
        warehouse.setName(warehouseDetails.getName());
        warehouse.setCapacity(warehouseDetails.getCapacity());
        return warehouseService.saveWarehouse(warehouse);
    }

    @DeleteMapping("/{id}") // Deletes a warehouse by id
    public void deleteWarehouse(@PathVariable Long id) {
        warehouseService.deleteWarehouse(id);
    }

    @GetMapping("/{id}/space-used") // Gets the current space used in a warehouse by id
    public double getCurrentSpaceUsed(@PathVariable Long id) {
        return warehouseService.getCurrentSpaceUsed(id);
    }
}