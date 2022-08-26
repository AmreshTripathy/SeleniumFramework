import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

/*
 * @Amresh Tripathy
 */

public class BrowserTest {
	
	public static void main(String[] args) {
		
//		System.setProperty("webdriver.chrome.driver",".\\drivers\\chromedriver\\chromedriver.exe");
		System.setProperty("webdriver.gecko.driver",".\\drivers\\geckodriver\\geckodriver.exe");
//		System.setProperty("webdriver.edge.driver",".\\drivers\\msedgedriver\\msedgedriver.exe");

		//		ChromeDriver driver = new ChromeDriver();
		WebDriver driver = new FirefoxDriver();
//		EdgeDriver driver = new EdgeDriver();
		
		driver.manage().window().maximize();
		driver.get("https://www.hackerrank.com/");
		driver.findElement(By.xpath("//*[contains(text(),'Login')]")).click();
		driver.findElement(By.xpath("//*[contains(@aria-label,\"allow cookies\")]")).click();
		driver.findElement(By.xpath("(//*[contains(@class,'fl-button-text')])[2]")).click();
		driver.close();
		
	}
	
}
