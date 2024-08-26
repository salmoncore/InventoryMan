package com.skillstorm.cucumber.UITests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import com.github.dockerjava.core.dockerfile.DockerfileStatement.Add;
import com.skillstorm.inventoryman.models.Warehouse;
import com.skillstorm.selenium.LandingPage;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;

public class NavBarSteps {
    
    private WebDriver driver;
    private LandingPage lPage;

    @Before("@navBarFunctionality")
    public void before() {
        ChromeOptions options = new ChromeOptions();

        if ("true".equals(System.getenv("HEADLESS"))) {
            options.addArguments("--headless");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--window-size=1920,1080");
            options.addArguments("--disable-gpu");
            options.addArguments("--remote-debugging-port=9222");
        }

        WebDriverManager.chromedriver().setup();

        this.driver = new ChromeDriver(options);
        this.lPage = new LandingPage(driver);
    }

    //Given I am on the InventoryMan landing page
    @Given("I am on the InventoryMan landing page")
    public void loadWebsite() {
        System.out.println("Step: I am on the InventoryMan landing page");
        this.lPage.get();
    }

    //When I select the InventoryMan button 
    @When("I select the InventoryMan button")
    public void selectInventoryMan() {
        System.out.println("Step: I select the InventoryMan button");
        this.lPage.selectInventoryMan();
    }

    //Then I should see that no other buttons are highlighted
    @Then("I should see that no other buttons are highlighted")
    public void noOtherButtonsHighlighted() {
        System.out.println("Step: I should see that no other buttons are highlighted");
        assertTrue(lPage.noOtherButtonsHighlighted());
    }

    //And that I am on the landing page for InventoryMan
    @Then("I am on the landing page for InventoryMan")
    public void onInventoryManLandingPage() {
        System.out.println("Step: I am on the landing page for InventoryMan");
        assertTrue(lPage.onInventoryManLandingPage());
    }

    //When I select the Warehouses button
    @When("I select the Warehouses button")
    public void selectWarehouses() {
        System.out.println("Step: I select the Warehouses button");
        this.lPage.selectWarehouses();
    }

    //Then I should see that the Warehouses button is highlighted
    @Then("I should see that the Warehouses button is highlighted")
    public void warehousesButtonHighlighted() {
        System.out.println("Step: I should see that the Warehouses button is highlighted");
        assertTrue(lPage.warehousesButtonHighlighted());
    }

    //And that I am on the Warehouses page
    @Then("I am on the Warehouses page")
    public void onWarehousesPage() {
        System.out.println("Step: I am on the Warehouses page");
        assertTrue(lPage.onWarehousesPage());
    }

    //When I select the Items button
    @When("I select the Items button")
    public void selectItems() {
        System.out.println("Step: I select the Items button");
        this.lPage.selectItems();
    }

    //Then I should see that the Items button is highlighted
    @Then("I should see that the Items button is highlighted")
    public void itemsButtonHighlighted() {
        System.out.println("Step: I should see that the Items button is highlighted");
        assertTrue(lPage.itemsButtonHighlighted());
    }

    //And that I am on the Items page
    @Then("I am on the Items page")
    public void onItemsPage() {
        System.out.println("Step: I am on the Items page");
        assertTrue(lPage.onItemsPage());
    }

    //When I select the Add Warehouse button
    @When("I select the Add Warehouse button")
    public void selectAddWarehouse() {
        System.out.println("Step: I select the Add Warehouse button");
        this.lPage.selectAddWarehouse();
    }

    //Then I should see that the Add Warehouse button is highlighted
    @Then("I should see that the Add Warehouse button is highlighted")
    public void addWarehouseButtonHighlighted() {
        System.out.println("Step: I should see that the Add Warehouse button is highlighted");
        assertTrue(lPage.addWarehouseButtonHighlighted());
    }

    //And that I am on the Add Warehouse page
    @Then("I am on the Add Warehouse page")
    public void onAddWarehousePage() {
        System.out.println("Step: I am on the Add Warehouse page");
        assertTrue(lPage.onAddWarehousePage());
    }

    //When I select the Add Item button
    @When("I select the Add Item button")
    public void selectAddItem() {
        System.out.println("Step: I select the Add Item button");
        this.lPage.selectAddItem();
    }

    //Then I should see that the Add Item button is highlighted
    @Then("I should see that the Add Item button is highlighted")
    public void addItemButtonHighlighted() {
        System.out.println("Step: I should see that the Add Item button is highlighted");
        assertTrue(lPage.addItemButtonHighlighted());
    }
    
    //And that I am on the Add Item page
    @Then("I am on the Add Item page")
    public void onAddItemPage() {
        System.out.println("Step: I am on the Add Item page");
        assertTrue(lPage.onAddItemPage());
    }

    @After("@navBarFunctionality") 
    public void after() {
        this.driver.quit();
    }
}
