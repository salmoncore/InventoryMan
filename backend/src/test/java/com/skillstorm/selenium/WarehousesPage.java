package com.skillstorm.selenium;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;


public class WarehousesPage {

    private WebDriver driver;
    private static final String url = "http://inventoryman.s3-website-us-east-1.amazonaws.com/warehouses";

    int warehouseCard = 0;

    // /html/body/div/div/div/form/div[1]/input for the warehouse name
    @FindBy(xpath = "/html/body/div/div/div/form/div[1]/input")
    private WebElement editWarehouseName;

    // /html/body/div/div/div/form/div[2]/input for the warehouse capacity
    @FindBy(xpath = "/html/body/div/div/div/form/div[2]/input")
    private WebElement editWarehouseCapacity;

    // /html/body/div/div/div/form/button for the Update button
    @FindBy(xpath = "/html/body/div/div/div/form/button")
    private WebElement updateButton;

    // select name 
    @FindBy(id = "sortCriteria")
    private WebElement selectName;

    public WarehousesPage(WebDriver driver) {
        this.driver = driver;
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        PageFactory.initElements(driver, this);
    }

    /**
     * Navigate to the Warehouses page
     */
    public void get() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.driver.get(url);
    }

    /**
     * Selects the sorting criteria 
     * @param sortingChoice
     */
    public void selectSortingOption(String sortingChoice) {
        try {
            Thread.sleep(1000);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }

        Select select = new Select(selectName);

        if (sortingChoice.equals("Name (Alphabetical)")) {
            select.selectByValue("name");
        } else {
            select.selectByValue("utilization");
        }
        
    }

    /**
     * Checks if warehouses are sorted by sorting criteria
     * @param sortingOrder
     */
    public boolean iswarehousesOrdered(String sortingOrder) {
        try {
            Thread.sleep(1000);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }

        if (sortingOrder.equals("alphabetical")) {
        
            List<String> values = allWarehouses();

            List<String> orderedValues = new ArrayList<>(values);
            Collections.sort(orderedValues, String.CASE_INSENSITIVE_ORDER);

            System.out.println(values);
            System.out.println(orderedValues);
            
            return orderedValues.equals(values);

        } else {
            List<WebElement> whElements = new ArrayList<>();
            whElements = driver.findElements(By.cssSelector("div.progress-bar.progress-bar-striped.bg-primary"));
            
            List<Integer> values = new ArrayList<>();
            for (WebElement w: whElements) {
                int util = 0;
                if (!"".equals((w.getText()))) {
                    String u = w.getText();
                    util = Integer.parseInt(u.replaceAll("%", ""));
                }
                values.add(util);
            }

            List<Integer> orderedValues = new ArrayList<>(values);
            Collections.sort(orderedValues, Collections.reverseOrder());

            return orderedValues.equals(values);
        }
    }

    /** 
     * Find a warehouse by name
     */
    public boolean findWarehouse(String warehouseName) {
        warehouseCard = -1; // setting warehouseCard for error checking
        try {
            Thread.sleep(1000);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
        List<WebElement> warehouses = new ArrayList<>();
        List<String> values = new ArrayList<>();
        for (int i = 1; i < 100; i++) {
            try {
                Thread.sleep(1000);
            } catch(InterruptedException e) {
                e.printStackTrace();
            }
            try {
                // /html/body/div/div/div/div/div[2]/div[<variable>]/div/h5 for the warehouse name
                WebElement warehouse = this.driver.findElement(By.xpath("/html/body/div/div/div/div/div[2]/div[" + i + "]/div/h5"));
                warehouses.add(warehouse);
                String warehouseText = warehouse.getText();
                values.add(warehouseText);
                if (warehouseText.equals(warehouseName)) {
                    warehouseCard = i; // save the index to warehouseCard
                    break; // breaks when found
                }
            } catch (Exception e) {
                break;
            }
        }
        System.out.println("Warehouse card number: " + warehouseCard);
        return values.contains(warehouseName);
    }

    /**
     * Using warehouseCard to select the edit button for clickEditButton
     */
    public void clickEditButton() {
        if (warehouseCard == -1) {
            return;
        }
        try {
            Thread.sleep(1000);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
        try {
            // /html/body/div/div/div/div/div[2]/div[<variable>]/div/div[2]/button[1] for the edit button
            WebElement editButton = this.driver.findElement(By.xpath("/html/body/div/div/div/div/div[2]/div[" + warehouseCard + "]/div/div[2]/button[1]"));
            //editButton.click();
            editButton.sendKeys(Keys.ENTER);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Check if the user is on the Edit Warehouse page
     */
    public boolean amIOnEditWarehousePage() {
        try {
            Thread.sleep(1000);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
        return this.driver.getCurrentUrl().contains("http://inventoryman.s3-website-us-east-1.amazonaws.com/edit-warehouse/");
    }

    /**
     * Clear and enter a string into the warehouse name field
     */
    public void enterWarehouseName(String warehouseName) {
        try {
            Thread.sleep(1000);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
        this.editWarehouseName.clear();
        this.editWarehouseName.sendKeys(warehouseName);
    }

    /**
     * Clear and enter a string into the warehouse capacity field
     */
    public void enterWarehouseCapacity(String warehouseCapacity) {
        try {
            Thread.sleep(1000);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
        this.editWarehouseCapacity.clear();
        this.editWarehouseCapacity.sendKeys(warehouseCapacity);
    }

    /**
     * Click the Update button to submit the warehouse
     */
    public void clickSubmitWarehouseButton() {
        try {
            Thread.sleep(1000);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
        //this.updateButton.click();
        this.updateButton.sendKeys(Keys.ENTER);
    }

    /**
     * Check if the user is on the Warehouses page
     */
    public boolean amIOnWarehousesPage() {
        try {
            Thread.sleep(1000);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
        return this.driver.getCurrentUrl().equals("http://inventoryman.s3-website-us-east-1.amazonaws.com/warehouses");
    }

    /**
     * Click the Delete button
     */
    public void clickDeleteButton() {
        if (warehouseCard == -1) {
            return;
        }
        try {
            Thread.sleep(1000);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
        try {
            // clickDeleteButton - /html/body/div/div/div/div/div[2]/div[<warehouseCard>]/div/div[2]/button[2]
            WebElement deleteButton = this.driver.findElement(By.xpath("/html/body/div/div/div/div/div[2]/div[" + warehouseCard + "]/div/div[2]/button[2]"));
            //deleteButton.click();
            deleteButton.sendKeys(Keys.ENTER);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Click the Ok dialog button from the browser alert
     */
    public void clickOKButton() {
        try {
            Thread.sleep(1000);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
        this.driver.switchTo().alert().accept();
    }
    /**
     * Gets the list of all warehouses on the Warehouses page
     */
    public List<String> allWarehouses () {
        List<WebElement> whElements = new ArrayList<>();
        List<String> values = new ArrayList<>();

        whElements = driver.findElements(By.tagName("h5"));
        
        for (WebElement w: whElements) {
            values.add(w.getText());
        }
        return values;
    }

    /**
     * Find the utilization bar for a warehouse and check if it matches the utilization provided. use the warehouseCard variable
     * @param warehouseName
     * @param utilization
     */
    public boolean findUtilization(String warehouseName, String utilization) {
        if (warehouseCard == -1) {
            return false;
        }
        try {
            Thread.sleep(1000);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
        try {
            // /html/body/div/div/div/div/div[2]/div[card]/div/div[1]/div for the utilization bar
            WebElement utilizationBar = this.driver.findElement(By.xpath("/html/body/div/div/div/div/div[2]/div[" + warehouseCard + "]/div/div[1]/div"));
            String utilizationText = utilizationBar.getText();
            return utilizationText.equals(utilization);
        } catch (Exception e) {
            return false;
        }
    }
}
