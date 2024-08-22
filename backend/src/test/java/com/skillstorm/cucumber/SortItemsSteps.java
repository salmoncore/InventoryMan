package com.skillstorm.cucumber;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import com.skillstorm.selenium.ItemsPage;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class SortItemsSteps {
    private WebDriver driver;
    private ItemsPage whPage;


    @Before("@sortItems")
    public void before() {
        ChromeOptions options = new ChromeOptions();
        this.driver = new ChromeDriver(options);
        this.whPage = new ItemsPage(driver);
    }

    @Given("I am in the Items page")
    public void loadWebsite() {
        System.out.println("Step: I am on the Items page");
        this.whPage.get();
    }

    @When("I select {string} in the Sort By dropdown to sort Items")
    public void selectSortingOption(String sortingOrder) {
        System.out.println("Step: I select to sort items by " + sortingOrder);
        this.whPage.selectSortingOption(sortingOrder);
    }

     @Then("Items should be displayed in the page by {string} order")
    public void isWarehousesOrdered(String sortingOrder) {
        System.out.println("Step: Items should be order by " + sortingOrder);
        assertTrue(whPage.iswarehousesOrdered(sortingOrder));
    }

    @After("@sortItems")
    public void tearDown() {
       if (driver != null) {
           this.driver.quit();
       }
    }
}
