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
    public void under25refund() {
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
    public void over25RefundTest() {
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

    @Test
    public void createTrackOver25() {

        // Enter product to search
        WebElement cprsearch = driver.findElement(By.cssSelector("input[type='text']"));
        driverWait.until(ExpectedConditions.visibilityOf(cprsearch));
        cprsearch.sendKeys("gopro");

        // Click the Search submit button from the homepage
        WebElement click11 = driver.findElement(By.xpath("//button[@class='cpr_searchSubmit']"));
        driverWait.until(ExpectedConditions.visibilityOf(click11));
        click11.click();

        //Category facet click
        WebElement category = driver.findElement(By.xpath("//a[contains(.,'Camera & Photo')]"));
        driverWait.until(ExpectedConditions.visibilityOf(category));
        category.click();

        //Category facet click2
        WebElement category2 = driver.findElement(By.xpath("//a[contains(.,'Camcorders')]"));
        driverWait.until(ExpectedConditions.visibilityOf(category2));
        category2.click();

        //Price facet click
        WebElement price = driver.findElement(By.xpath("//a[contains(.,'$225 - $')]"));
        driverWait.until(ExpectedConditions.visibilityOf(price));
        price.click();

        //Merchant facet click
        WebElement merchant = driver.findElement(By.xpath("//a[contains(.,'BeachCamera.com')]"));
        driverWait.until(ExpectedConditions.visibilityOf(merchant));
        merchant.click();

        //Select product to track
        List<WebElement> buttons = driver.findElements(By.className("cpr_trackBtn"));
        WebElement query_enquirymode = buttons.get(0);
        query_enquirymode.click();

        //Create a track
        driverWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.id("cpr_SPR_price")));
        WebElement trackprice = driver.findElement(By.id("cpr_SPR_price"));
        WebElement date = driver.findElement(By.id("cpr_SPR_date"));
        WebElement store = driver.findElement(By.id("cpr_SPR_place"));
        WebElement StartButton = driver.findElement(By.id("cpr_SPR_submit"));

        trackprice.sendKeys("500");
        date.sendKeys("08/15/2015");
        store.click();
        store.sendKeys("Amazon");
        StartButton.click();

        //Edit Purchase Details
        WebElement editPurchaseDetails = driver.findElement(By.linkText("Edit Purchase Details"));
        driverWait.until(ExpectedConditions.visibilityOf(editPurchaseDetails));
        editPurchaseDetails.click();

        driver.findElement(By.id("cpr_SPR_price")).clear();
        driver.findElement(By.id("cpr_SPR_date")).clear();
        driver.findElement(By.id("cpr_SPR_place")).clear();

        //Edit a track
        driverWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.id("cpr_SPR_price")));
        WebElement trackprice1 = driver.findElement(By.id("cpr_SPR_price"));
        WebElement date1 = driver.findElement(By.id("cpr_SPR_date"));
        WebElement store1 = driver.findElement(By.id("cpr_SPR_place"));
        WebElement StartButton1 = driver.findElement(By.id("cpr_SPR_submit"));

        trackprice1.sendKeys("399.99");
        date1.sendKeys("08/10/2015");
        store1.click();
        store1.sendKeys("Target");
        StartButton1.click();

        //Cancel Rewind no
        driver.findElement(By.xpath("//a[@class='cpr_cancelToggle']")).click();
        //Click No to collapse toggle
        WebElement cancel = driver.findElement(By.xpath("//a[@class='cpr_cancelToggle']"));
        driverWait.until(ExpectedConditions.visibilityOf(cancel));
        cancel.click();

        //Download Claim form
        // WebElement claimForm = driver.findElement(By.linkText("claim form"));
        // driverWait.until(ExpectedConditions.visibilityOf(claimForm));
        // claimForm.click();

        //Upload receipt
        WebElement receipt = driverWait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".cpr_frame input[name='receipt']")));
        //receipt.click();
        receipt.sendKeys("C:\\Users\\frank.marchion\\Documents\\Test Receipts\\CARTERA TEST RECEIPT PNG.png");

        //Hide receipt
        driver.findElement(By.linkText("Hide Receipt")).click();
        //Click to expand receipt container
        WebElement expand = driver.findElement(By.linkText("Upload or View Receipt(s)"));
        driverWait.until(ExpectedConditions.visibilityOf(expand));
        expand.click();
    }

    @Test
    public void createTrackUnder25() {
        // Enter product to search
        WebElement cprsearch = driver.findElement(By.cssSelector("input[type='text']"));
        driverWait.until(ExpectedConditions.visibilityOf(cprsearch));
        cprsearch.sendKeys("gopro");

        // Click the Search submit button from the homepage
        WebElement click11 = driver.findElement(By.xpath("//button[@class='cpr_searchSubmit']"));
        driverWait.until(ExpectedConditions.visibilityOf(click11));
        click11.click();

        //Category facet click
        WebElement category = driver.findElement(By.xpath("//a[contains(.,'Camera & Photo')]"));
        driverWait.until(ExpectedConditions.visibilityOf(category));
        category.click();

        //Category facet click2
        WebElement category2 = driver.findElement(By.xpath("//a[contains(.,'Camcorders')]"));
        driverWait.until(ExpectedConditions.visibilityOf(category2));
        category2.click();

        //Price facet click
        WebElement price = driver.findElement(By.xpath("//a[contains(.,'$225 - $')]"));
        driverWait.until(ExpectedConditions.visibilityOf(price));
        price.click();

        //Merchant facet click
        WebElement merchant = driver.findElement(By.xpath("//a[contains(.,'BeachCamera.com')]"));
        driverWait.until(ExpectedConditions.visibilityOf(merchant));
        merchant.click();

        //Select product to track, Gopro - Hero4 Silver Action Camera, 1st position
        List<WebElement> buttons = driver.findElements(By.className("cpr_trackBtn"));
        WebElement query_enquirymode = buttons.get(0);
        query_enquirymode.click();

        //Create a track
        driverWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.id("cpr_SPR_price")));
        WebElement trackprice = driver.findElement(By.id("cpr_SPR_price"));
        WebElement date = driver.findElement(By.id("cpr_SPR_date"));
        WebElement store = driver.findElement(By.id("cpr_SPR_place"));
        WebElement StartButton = driver.findElement(By.id("cpr_SPR_submit"));

        trackprice.sendKeys("500");
        date.sendKeys("08/15/2015");
        store.click();
        store.sendKeys("Amazon");
        StartButton.click();

        //Edit Purchase Details
        WebElement editPurchaseDetails = driver.findElement(By.linkText("Edit Purchase Details"));
        driverWait.until(ExpectedConditions.visibilityOf(editPurchaseDetails));
        editPurchaseDetails.click();

        driver.findElement(By.id("cpr_SPR_price")).clear();
        driver.findElement(By.id("cpr_SPR_date")).clear();
        driver.findElement(By.id("cpr_SPR_place")).clear();

        //Edit a track
        driverWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.id("cpr_SPR_price")));
        WebElement trackprice1 = driver.findElement(By.id("cpr_SPR_price"));
        WebElement date1 = driver.findElement(By.id("cpr_SPR_date"));
        WebElement store1 = driver.findElement(By.id("cpr_SPR_place"));
        WebElement StartButton1 = driver.findElement(By.id("cpr_SPR_submit"));

        trackprice1.sendKeys("365.00");
        date1.sendKeys("08/10/2015");
        store1.click();
        store1.sendKeys("Target");
        StartButton1.click();

        //Cancel Rewind no
        driver.findElement(By.xpath("//a[@class='cpr_cancelToggle']")).click();
        //Click No to collapse toggle
        WebElement cancel = driver.findElement(By.xpath("//a[@class='cpr_cancelToggle']"));
        driverWait.until(ExpectedConditions.visibilityOf(cancel));
        cancel.click();

        //Download Claim form
        // WebElement claimForm = driver.findElement(By.linkText("claim form"));
        // driverWait.until(ExpectedConditions.visibilityOf(claimForm));
        // claimForm.click();

        //Upload receipt
        WebElement receipt = driverWait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".cpr_frame input[name='receipt']")));
        //receipt.click();
        receipt.sendKeys("C:\\Users\\frank.marchion\\Documents\\Test Receipts\\CARTERA TEST RECEIPT JPG.jpg");

        //Hide receipt
        driver.findElement(By.linkText("Hide Receipt")).click();
        //Click to expand receipt container
        WebElement expand = driver.findElement(By.linkText("Upload or View Receipt(s)"));
        driverWait.until(ExpectedConditions.visibilityOf(expand));
        expand.click();

    }

    @Test
    public void deleteTrack() {
        // Click on My Price Rewinds link in header
        WebElement myPriceRewinds = driver.findElement(By.xpath("//a[contains(.,'My Price Rewinds')]"));
        driverWait.until(ExpectedConditions.visibilityOf(myPriceRewinds));
        myPriceRewinds.click();

        // Select first track
        List<WebElement> selectTrack1 = driverWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//a[@class='cpr_trackBtn']")));
        selectTrack1.get(0).click();

        //Get track id
        String trackId = driver.findElement(By.cssSelector("div.cpr_refNum span")).getText();
        System.out.println("Reference #: " + trackId);

        //Cancel Rewind yes
        driver.findElement(By.xpath("//a[@class='cpr_cancelToggle']")).click();
        WebElement cancelYes = driver.findElement(By.xpath("//a[contains(.,'Yes')]"));
        driverWait.until(ExpectedConditions.visibilityOf(cancelYes));
        cancelYes.click();


        if (driver.getPageSource().contains("trackId")) {
            System.out.println("Track has not been deleted");
        } else {
            System.out.println("Track has been deleted");

        //Click the browser back button after track is deleted and verify error message
            driverWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//a[@class='cpr_trackBtn']")));
            driver.navigate().back();
            driver.navigate().refresh();
            String sysErr = driver.findElement(By.className("cpr_serverError")).getText();
            System.out.println("Error message on page: " + sysErr);
            //System.out.println("Refund Amount : " + elem.findElement(By.cssSelector(".cpr_refund span")).getText().substring(1));
            //  List<WebElement> selectTrack1 = driverWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//div[@class='cpr_sm-6 cpr_md-4']")));
            //  selectTrack1.get(0).click();


        }
    }
}
