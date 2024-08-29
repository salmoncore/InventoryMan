package com.skillstorm.unit_testing.controllers;

import java.util.List;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.skillstorm.inventoryman.controllers.WarehouseController;
import com.skillstorm.inventoryman.models.Warehouse;
import com.skillstorm.inventoryman.services.WarehouseService;


public class WarehouseControllerTest {
    
    @Mock
    private WarehouseService whService;

    @InjectMocks
    private WarehouseController whController;
    private AutoCloseable closeable;

    @BeforeTest
    public void setup() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getAllWarehousesTest() {
        
        List<Warehouse> expectedWH = Arrays.asList(new Warehouse(), new Warehouse());

        when(whService.getAllWarehouses()).thenReturn(expectedWH);

        List<Warehouse> response = whController.getAllWarehouses();

        Assert.assertEquals(response, expectedWH);
    }

    @Test
    public void getWarehouseByIdTest() {
        long warehouseId = 1;
        Warehouse expectedWH = new Warehouse();
        when(whService.getWarehouseById(warehouseId)).thenReturn(expectedWH);

        Warehouse response = whController.getWarehouseById(warehouseId);

        Assert.assertEquals(response, expectedWH);
    }

    @Test
    public void createWarehouseTest() {
        Warehouse inputWH = new Warehouse();
        Warehouse savedWH = new Warehouse();

        when(whService.saveWarehouse(inputWH)).thenReturn(savedWH);

        Warehouse response = whController.createWarehouse(inputWH);

        Assert.assertEquals(response, savedWH);
    }

    @Test
    public void updateWarehouseTest() {
        long warehouseId = 1;

        Warehouse inputWH = new Warehouse();
        Warehouse updatedWH = new Warehouse();

        when(whService.getWarehouseById(warehouseId)).thenReturn(inputWH);
        when(whService.saveWarehouse(inputWH)).thenReturn(updatedWH);

        Warehouse response = whController.updateWarehouse(warehouseId, inputWH);

        Assert.assertEquals(response, updatedWH);
    }

    @Test
    public void deleteWarehouseTest() {
        long warehouseId = 1;
        Warehouse expectedWH = new Warehouse();

        when(whService.getWarehouseById(warehouseId)).thenReturn(expectedWH);

        assertAll(() -> whController.deleteWarehouse(warehouseId));
    }

    @Test
    public void getCurrentSpaceUsedTest() {
        Warehouse inputWarehouse = new Warehouse();
        Warehouse expectedWH = new Warehouse();

        when(whService.getCurrentSpaceUsed(inputWarehouse.getId())).thenReturn(expectedWH.getUsedCapacity());

        double response = whController.getCurrentSpaceUsed(inputWarehouse.getId());

        Assert.assertEquals(response, expectedWH.getUsedCapacity());

    }

    @AfterTest
    public void teardown() throws Exception{
        closeable.close();
    }
}
