import java.io.File;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

/*
 * @Amresh Tripathy
 */

public class HackerRank extends CPA_Selenium {
	
	protected static final Logger LOG = Logger.getLogger("BrowserTest.class");
	
	protected void login(String account) {
		
//		System.setProperty("webdriver.chrome.driver",".\\drivers\\chromedriver\\chromedriver.exe");
//		System.setProperty("webdriver.gecko.driver",".\\drivers\\geckodriver\\geckodriver.exe");
//		System.setProperty("webdriver.edge.driver",".\\drivers\\msedgedriver\\msedgedriver.exe");

		//		ChromeDriver driver = new ChromeDriver();
//		WebDriver driver = new FirefoxDriver();
//		EdgeDriver driver = new EdgeDriver();
		
		WebDriver driver = new FirefoxDriver();
		try {
			File downloadFolder = new File(".\\screenshots");
			FileUtils.cleanDirectory(downloadFolder);
			driver.manage().window().maximize();
			driver.get("https://www.hackerrank.com/");
			driver.findElement(By.xpath("//*[contains(text(),'Login')]")).click();
			Thread.sleep(5000);
			driver.findElement(By.xpath("//*[contains(@aria-label,\"allow cookies\")]")).click();
			driver.findElement(By.xpath("(//*[contains(@class,'fl-button-text')])[2]")).click();
			takeScreenShot(driver, downloadFolder.getAbsolutePath()+ "\\beforeLogin.jpg");
			driver.findElement(By.name("username")).sendKeys(credentials.get(userId));
			driver.findElement(By.name("password")).sendKeys(credentials.get(password));
			driver.findElement(By.xpath("//*[contains(text(),'Log In')]")).click();
			Thread.sleep(3000);
			String pageSource = driver.getPageSource();
			if(pageSource.contains("amresh")) {
				LOG.debug("BrowserTest :: login Successful");
				takeScreenShot(driver, downloadFolder.getAbsolutePath()+ "\\login.jpg");
				Thread.sleep(3000);
				search(driver);
				driver.findElement(By.xpath("//*[contains(@data-analytics,\"NavBarProfileDropDown\")]")).click();
				Thread.sleep(3000);
				driver.findElement(By.xpath("//*[contains(text(),\"Logout\")]")).click();
				takeScreenShot(driver, downloadFolder.getAbsolutePath()+ "\\logout.jpg");
				LOG.debug("BrowserTest :: logout Completed for user " + account);
			}else {
				LOG.debug("login Failed");
			}
		}catch(Exception e) {
			LOG.error("BrowserTest :: Exception Occured"+e.getMessage(),e);
			try {
				takeScreenShot(driver, ".\\\\screenshots\\\\error.jpg");
			} catch (Exception e1) {
				LOG.error("Exception Occured while taking ScreenShot");
			}
		}finally{
			driver.close();
		}
		
	}
	
	protected static boolean search(WebDriver driver) {
		try {
			WebElement searchEnter =  driver.findElement(By.xpath("//*[contains(@placeholder,\"Search\")]"));
			searchEnter.sendKeys("plus minus" + Keys.ENTER);
			Thread.sleep(3000);
			searchEnter.sendKeys(Keys.ENTER);
//			((JavascriptExecutor)driver).executeScript("window.scrollBy(0,890)", "");
			Thread.sleep(5000);
			((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.xpath("//label[contains(@aria-label,\"Select Your Coding Language\")]")));
			Thread.sleep(2000);
			WebElement languageSelector = driver.findElement(By.xpath("//input[contains(@id,\"select-language-input\")]"));
			languageSelector.sendKeys("python 3" + Keys.ENTER);
			
			return true;
		}catch(Exception e) {
			LOG.error("BrowserTest :: Exception Occured in side Search method"+e.getMessage(),e);
		}
		return false;
	}
	
	public static void fileSelector() {
		
	}
	
	public static void takeScreenShot(WebDriver driver, String path) throws Exception {
		TakesScreenshot img = ((TakesScreenshot)driver);
		File srcFile = img.getScreenshotAs(OutputType.FILE);
		File destFile = new File(path);
		FileUtils.copyFile(srcFile, destFile);
	}
	
}
