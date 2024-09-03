package com.skillstorm.unit_testing.controllers;

import java.util.List;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.skillstorm.inventoryman.controllers.ItemController;
import com.skillstorm.inventoryman.models.Item;
import com.skillstorm.inventoryman.models.Warehouse;
import com.skillstorm.inventoryman.services.ItemService;


public class ItemControllerTest {
    
    @Mock
    private ItemService itService;

    @InjectMocks
    private ItemController itController;
    private AutoCloseable closeable;

    @BeforeTest
    public void setup() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getAllItemsTest() {
        
        List<Item> expectedItems = Arrays.asList(new Item(), new Item());

        when(itService.getAllItems()).thenReturn(expectedItems);

        List<Item> response = itController.getAllItems();

        Assert.assertEquals(response, expectedItems);
    }

    @Test
    public void getItemByIdTest() {
        long itemId = 1;
        Item expectedItem = new Item();
        when(itService.getItemById(itemId)).thenReturn(expectedItem);

        Item response = itController.getItemById(itemId);

        Assert.assertEquals(response, expectedItem);
    }

    @Test
    public void createItemTest() {
        Item inputItem = new Item();
        Item savedItem = new Item();

        when(itService.saveItem(inputItem)).thenReturn(savedItem);

        ResponseEntity<Item> response = itController.createItem(inputItem);

        Assert.assertEquals(response.getBody(), savedItem);
        Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    /**
     * Testing if an item with null value for warehouse would throw an error and return a BAD_REQUEST http status code
     */
    @Test
    public void createItemTes2() {

        Item inputItem = new Item();
        Warehouse wh = new Warehouse();

        inputItem.setId(1L);
        inputItem.setName("shirt");
        inputItem.setDescription("Top clothes");
        inputItem.setQuantity(20);
        inputItem.setSizeInCubicFt(20);
        inputItem.setWarehouse(wh);

        when(itService.saveItem(inputItem)).thenThrow(IllegalArgumentException.class);
        
        Assert.assertEquals(itController.createItem(inputItem).getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    public void updateItemTest() {
        Item inputItem = new Item();
        Item updatedItem = new Item();

        when(itService.updateItem(inputItem.getId(), inputItem)).thenReturn(updatedItem);

        ResponseEntity<Item> response = itController.updateItem(inputItem.getId(), inputItem);

        Assert.assertEquals(response.getBody(), updatedItem);
        Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

        /**
     * Testing if an item with null value for warehouse would throw an error and return a BAD_REQUEST http status code
     */
    @Test
    public void updateItemTes2() {

        Item inputItem = new Item();
        Item updatedItem = new Item();
        Warehouse wh = new Warehouse();

        inputItem.setId(1L);
        inputItem.setName("shirt");
        inputItem.setDescription("Top clothes");
        inputItem.setQuantity(20);
        inputItem.setSizeInCubicFt(20);
        inputItem.setWarehouse(wh);

        updatedItem.setId(1L);
        updatedItem.setName("shirt");
        updatedItem.setDescription("Top clothes");
        updatedItem.setQuantity(20);
        updatedItem.setSizeInCubicFt(20);
        updatedItem.setWarehouse(wh);

        when(itService.updateItem(1L, inputItem)).thenThrow(IllegalArgumentException.class);
        
        Assert.assertEquals(itController.updateItem(1L, inputItem).getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    public void deleteItemTest() {
        long itemId = 1;

        Item deletedItem = new Item();
        when(itService.getItemById(itemId)).thenReturn(deletedItem);

        assertAll(() -> itController.deleteItem(itemId));
    }

    @Test
    public void deleteItemTest2() {
        long itemId = 1;

        Item deletedItem = new Item();
        Warehouse wh = new Warehouse();

        deletedItem.setId(1L);
        deletedItem.setName("shirt");
        deletedItem.setDescription("Top clothes");
        deletedItem.setQuantity(20);
        deletedItem.setSizeInCubicFt(20);
        deletedItem.setWarehouse(wh);

        when(itService.getItemById(itemId)).thenReturn(deletedItem);

        assertAll(() -> itController.deleteItem(itemId));
    }

    @AfterTest
    public void teardown() throws Exception{
        closeable.close();
    }
}
