import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by frank.marchion on 12/8/2015.
 */



public class cprRegressionTest {
    private WebDriver driver;
    private WebDriverWait driverWait;

    @BeforeTest
    public void testInitialize() {
        this.driver = new FirefoxDriver();
        this.driverWait = new WebDriverWait(driver, 60);

        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);

        driver.get("http://silver.www.citipricerewind.com/demo/citi_prw_sso.php");
        WebElement payload = driver.findElement(By.cssSelector("input[value='Single Card Payload']"));
        payload.click();
        driver.manage().window().maximize();
    }

    @Test
    public void under25refund()
    {
        // Click on My Price Rewinds link in header
        WebElement myPriceRewinds = driver.findElement(By.xpath("//a[contains(.,'My Price Rewinds')]"));
        driverWait.until(ExpectedConditions.visibilityOf(myPriceRewinds));
        myPriceRewinds.click();

        // Click on 'Filter by' drop-down
        WebElement filterBy = driver.findElement(By.xpath("//span[contains(@class,'cpr_dynamic')]"));
        driverWait.until(ExpectedConditions.visibilityOf(filterBy));
        filterBy.click();

        // Click on 'Request Refund'
        WebElement RequestRefund2 = driver.findElement(By.xpath("//ul/li/a[contains(., 'Request Refund')]"));
        //WebElement RequestRefund2 = driver.findElement(By.xpath("//a[contains(.,'Request Refund')]"))
        driverWait.until(ExpectedConditions.visibilityOf(RequestRefund2));
        RequestRefund2.click();

        //Click on 'View' drop-down
        WebElement viewBy = driver.findElement(By.xpath("//span[contains(.,'9 per page')]"));
        driverWait.until(ExpectedConditions.visibilityOf(viewBy));
        viewBy.click();

        // Limit 26 per page
        WebElement perPage26 = driver.findElement(By.xpath("//a[contains(.,'26 per page')]"));
        driverWait.until(ExpectedConditions.elementToBeClickable(perPage26));
        perPage26.click();

        List<WebElement> AllTrackList = driver.findElements(By.cssSelector(".cpr_trackContent"));
        for (WebElement elem : AllTrackList) {
            ////a[contains(text(), 'Request Refund')]
            //elem.findElement(By.xpath("//a[contains(@class,'cpr_trackBtn') and .//text() = 'Request Refund']"))
            //if (elem.getText().equalsIgnoreCase("Request Refund")) {

            //if (elem.findElement(By.xpath("//a[contains(text(), 'Request Refund')]")).isDisplayed()) {
            if (elem.findElement(By.cssSelector("a.cpr_trackBtn")).getText().equalsIgnoreCase("Request Refund")) {
                //System.out.print("hello");
                if ((Double.parseDouble(elem.findElement(By.cssSelector(".cpr_refund span")).getText().substring(1)) < 25)) {
                    System.out.println("Refund Amount : " + elem.findElement(By.cssSelector(".cpr_refund span")).getText().substring(1));
                    elem.click();
                    break;
                }
            }
        }

        driverWait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div.cpr_trackStatus")));

        // Click on 'Request Refund button' again
        WebElement refund1 = driverWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[contains(@class,'cpr_md-only')]")));
        //driverWait.until(ExpectedConditions.visibilityOf(refund1));
        refund1.click();

        // Click on 'Start Online'
        WebElement start = driver.findElement(By.linkText("Start Online"));
        driverWait.until(ExpectedConditions.visibilityOf(start));
        start.click();

        // Step 1 Terms and Conditions. Enter name and last 4 digits of credit card number.
        driverWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@id = 'cpr_RS3_fullName']")));
        WebElement name1 = driver.findElement(By.xpath("//input[@id = 'cpr_RS3_fullName']"));
        WebElement last4edit = driver.findElement(By.id("cpr_RS3_lastFour"));
        name1.clear();
        name1.sendKeys("CARTERA TEST");
        last4edit.clear();
        last4edit.sendKeys("9999");

        // Click on 'Continue to Step 2'
        WebElement step2 = driver.findElement(By.xpath("//button[@id='cpr_RS3_submitForm']"));
        driverWait.until(ExpectedConditions.visibilityOf(step2));
        step2.click();

        // Click Edit link
        WebElement edit = driverWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[@class = 'cpr_RS4_editLink']")));
        edit.click();

        // Step 1 Terms and Conditions. Edit name and last 4 digits of credit card number.
        driverWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@id = 'cpr_RS3_fullName']")));
        WebElement editname1 = driver.findElement(By.xpath("//input[@id = 'cpr_RS3_fullName']"));
        WebElement editlast4 = driver.findElement(By.id("cpr_RS3_lastFour"));
        editname1.clear();
        editname1.sendKeys("TEST TEST");
        editlast4.clear();
        editlast4.sendKeys("1234");

        // Click on 'Continue to Step 2'
        WebElement step2cont = driver.findElement(By.xpath("//button[@id='cpr_RS3_submitForm']"));
        driverWait.until(ExpectedConditions.visibilityOf(step2cont));
        step2cont.click();

        //View Authorization Certification
        WebElement viewAuth = driverWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[contains(.,'View Authorization Certification')]")));
        viewAuth.click();

        //View State Information Disclosures
        WebElement viewState = driverWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[contains(.,'View State Information Disclosures')]")));
        viewState.click();

        //Submit Refund Request
        WebElement submitRef = driver.findElement(By.linkText("Submit Refund Request"));
        submitRef.click();

        //WebElement confirm = driver.findElement(By.cssSelector("div.cpr_RSC_container"));
        WebElement confirm = driverWait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div.cpr_RSC_container")));
        String string = confirm.getText();
        System.out.println(string);
        //Assert.assertTrue(confirm.getText().equalsIgnoreCase("Thank You! Your Refund Has Been Requested"), "Error: 'Thank you text is not present'");
        Assert.assertTrue(confirm.getText().contains("Thank You! Your Refund Has Been Requested"), "Error: 'Thank you text is not present'");
    }

    @Test
    public void over25RefundTest()
    {
        // Click on My Price Rewinds link in header
        WebElement myPriceRewinds = driver.findElement(By.xpath("//a[contains(.,'My Price Rewinds')]"));
        driverWait.until(ExpectedConditions.visibilityOf(myPriceRewinds));
        myPriceRewinds.click();

        // Click on 'Filter by' drop-down
        WebElement filterBy = driver.findElement(By.xpath("//span[contains(@class,'cpr_dynamic')]"));
        driverWait.until(ExpectedConditions.visibilityOf(filterBy));
        filterBy.click();

        // Click on 'Request Refund'
        WebElement RequestRefund2 = driver.findElement(By.xpath("//ul/li/a[contains(., 'Request Refund')]"));
        //WebElement RequestRefund2 = driver.findElement(By.xpath("//a[contains(.,'Request Refund')]"))
        driverWait.until(ExpectedConditions.visibilityOf(RequestRefund2));
        RequestRefund2.click();

        //Click on 'View' drop-down
        WebElement viewBy = driver.findElement(By.xpath("//span[contains(.,'9 per page')]"));
        driverWait.until(ExpectedConditions.visibilityOf(viewBy));
        viewBy.click();

        // Limit 26 per page
        WebElement perPage26 = driver.findElement(By.xpath("//a[contains(.,'26 per page')]"));
        driverWait.until(ExpectedConditions.elementToBeClickable(perPage26));
        perPage26.click();

        List<WebElement> AllTrackList = driver.findElements(By.cssSelector(".cpr_trackContent"));
        for (WebElement elem : AllTrackList) {
            ////a[contains(text(), 'Request Refund')]
            //elem.findElement(By.xpath("//a[contains(@class,'cpr_trackBtn') and .//text() = 'Request Refund']"))
            //if (elem.getText().equalsIgnoreCase("Request Refund")) {

            //if (elem.findElement(By.xpath("//a[contains(text(), 'Request Refund')]")).isDisplayed()) {
            if (elem.findElement(By.cssSelector("a.cpr_trackBtn")).getText().equalsIgnoreCase("Request Refund")) {
                //System.out.print("hello");
                if ((Double.parseDouble(elem.findElement(By.cssSelector(".cpr_refund span")).getText().substring(1)) > 25)) {
                    System.out.println("Refund Amount : " + elem.findElement(By.cssSelector(".cpr_refund span")).getText().substring(1));
                    elem.click();
                    break;
                }
            }
        }

        driverWait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div.cpr_trackStatus")));

        // Click on 'Request Refund button' again
        WebElement refund1 = driverWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[contains(@class,'cpr_md-only')]")));
        //driverWait.until(ExpectedConditions.visibilityOf(refund1));
        refund1.click();

        // Click on 'Start Online'
        WebElement start = driver.findElement(By.linkText("Start Online"));
        driverWait.until(ExpectedConditions.visibilityOf(start));
        start.click();

        //Upload receipt
        WebElement receipt1 = driverWait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".cpr_frame input[name='receipt']")));
        //receipt.click();
        receipt1.sendKeys("C:\\Users\\frank.marchion\\Documents\\Test Receipts\\CARTERA TEST RECEIPT JPG.jpg");

        // Click on 'Continue to Step 2'
        WebElement step2 = driver.findElement(By.linkText("Continue to Step 2"));
        driverWait.until(ExpectedConditions.visibilityOf(step2));
        step2.click();

        // Click on 'Continue to Step 3'
        WebElement step3 = driver.findElement(By.xpath("//button[@class ='cpr_btn']"));
        driverWait.until(ExpectedConditions.visibilityOf(step3));
        step3.click();

        //Enter name
        WebElement name = driverWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@id = 'cpr_RS3_fullName']")));
        name.clear();
        name.sendKeys("TEST TEST");

        //Enter Credit Card last 4 digits
        WebElement last4 = driverWait.until(ExpectedConditions.presenceOfElementLocated(By.id("cpr_RS3_lastFour")));
        last4.clear();
        last4.sendKeys("9999");

        // Click on 'Continue to Step 4'
        WebElement step4 = driver.findElement(By.xpath("//button[@id='cpr_RS3_submitForm']"));
        driverWait.until(ExpectedConditions.visibilityOf(step4));
        step4.click();

        //Edit Payment Method
        WebElement edit = driverWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[@class = 'cpr_RS4_editLink']")));
        edit.click();

        //Change payment method to Check
        WebElement check = driverWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@id = 'paymentMethodCheck']")));
        check.click();

        // Click on 'Continue to Step 3' hello
        WebElement step3Next = driver.findElement(By.xpath("//button[@class ='cpr_btn']"));
        driverWait.until(ExpectedConditions.visibilityOf(step3Next));
        step3Next.click();

        //Enter name
        WebElement name1 = driverWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@id = 'cpr_RS3_fullName']")));
        name1.clear();
        name1.sendKeys("CARTERA TEST");

        //Enter Credit Card last 4 digits
        WebElement last4edit = driverWait.until(ExpectedConditions.presenceOfElementLocated(By.id("cpr_RS3_lastFour")));
        last4edit.clear();
        last4edit.sendKeys("1234");

        // Click on 'Continue to Step 4'
        WebElement step4edit = driver.findElement(By.xpath("//button[@id='cpr_RS3_submitForm']"));
        driverWait.until(ExpectedConditions.visibilityOf(step4edit));
        step4edit.click();

        //View Authorization Certification
        WebElement viewAuth = driverWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[contains(.,'View Authorization Certification')]")));
        viewAuth.click();

        //View State Information Disclosures
        WebElement viewState = driverWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[contains(.,'View State Information Disclosures')]")));
        viewState.click();

        //Submit Refund Request
        WebElement submitRef = driver.findElement(By.linkText("Submit Refund Request"));
        submitRef.click();

        WebElement confirm = driverWait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div.cpr_RSC_container")));
        String string = confirm.getText();
        System.out.println(string);
        //Assert.assertTrue(confirm.getText().equalsIgnoreCase("Thank You! Your Refund Has Been Requested"), "Error: 'Thank you text is not present'");
        Assert.assertTrue(confirm.getText().contains("Thank You! Your Refund Has Been Requested"), "Error: 'Thank you text is not present'");

    }

}
