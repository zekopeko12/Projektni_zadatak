import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class FirstTest {

    public WebDriver driver;
    public WebDriverWait wait;
    public String testUrl = "https://the-internet.herokuapp.com/";

    @BeforeMethod
    public void setupTest(){
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        driver = new ChromeDriver();
        driver.navigate().to(testUrl);

        wait = new WebDriverWait(driver, 2);
    }

    @Test
    public void verifyHomePageTitle() {
        wait.until(ExpectedConditions.titleIs("The Internet"));
        Assert.assertEquals(driver.getTitle(), "The Internet");
    }


    @Test
    public void addRemoveElementTest() {

        driver.findElement(By.linkText("Add/Remove Elements")).click();

        WebElement addButton = driver.findElement(By.xpath("//button[text()='Add Element']"));
        addButton.click();

        WebElement deleteButton = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.className("added-manually"))
        );

        Assert.assertTrue(deleteButton.isDisplayed());

        deleteButton.click();

        Assert.assertTrue(driver.findElements(By.className("added-manually")).isEmpty());
    }


    @Test
    public void javascriptAlertTest() {

        driver.findElement(By.linkText("JavaScript Alerts")).click();

        driver.findElement(By.xpath("//button[text()='Click for JS Alert']")).click();

        wait.until(ExpectedConditions.alertIsPresent());

        driver.switchTo().alert().accept();

        WebElement resultText = driver.findElement(By.id("result"));

        Assert.assertEquals(resultText.getText(), "You successfully clicked an alert");
    }


    @Test
    public void checkboxTest() {
        driver.findElement(By.linkText("Checkboxes")).click();

        WebElement checkbox1 = driver.findElement(By.cssSelector("input[type='checkbox']:nth-of-type(1)"));
        WebElement checkbox2 = driver.findElement(By.cssSelector("input[type='checkbox']:nth-of-type(2)"));

        if (!checkbox1.isSelected()) {
            checkbox1.click();
        }

        if (checkbox2.isSelected()) {
            checkbox2.click();
        }

        Assert.assertTrue(checkbox1.isSelected());
        Assert.assertFalse(checkbox2.isSelected());
    }


    @Test
    public void dropdownTest() {
        driver.findElement(By.linkText("Dropdown")).click();

        WebElement dropdown = driver.findElement(By.id("dropdown"));
        dropdown.click();

        WebElement option2 = driver.findElement(By.xpath("//option[text()='Option 2']"));
        option2.click();

        Assert.assertTrue(option2.isSelected());
    }

    @AfterMethod
    public void teardownTest(){
        driver.quit();
    }
}
