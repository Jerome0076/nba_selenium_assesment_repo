package nba_tests;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

public class NBA_Assesment {
	
	ChromeDriver driver;
	WebDriverWait wait;

	@Test
	public void testNBAPlayer() throws InterruptedException
	{
				
//	1. Navigate to https://www.nba.com/stats
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-notifications");
		driver = new ChromeDriver(options);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(25));
		driver.manage().window().maximize();
		wait = new WebDriverWait(driver, Duration.ofSeconds(150));
		driver.get("https://www.nba.com/stats");
		
	//Hanlding the cookie
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("onetrust-accept-btn-handler"))));
		driver.findElement(By.id("onetrust-accept-btn-handler")).click();
		handleAndCloseBanner();
		Thread.sleep(10000);

//	2. Click on 'See All Player Stats'
		driver.findElement(By.linkText("See All Player Stats")).click();
		Thread.sleep(15000);
		handleAndCloseBanner();

//	3. Choose 'Season' as '2023-24'
		Select dropdownSeason = new Select(driver.findElement(By.xpath("//label/p[text()='Season']/following::select[1]")));
		dropdownSeason.selectByValue("2023-24");
		
//	4. Choose 'Season Type' as 'NBA Cup'
		Select dropdownSeasonType = new Select(driver.findElement(By.xpath("//label/p[text()='Season Type']/following::select[1]")));
		dropdownSeasonType.selectByValue("IST");
		
//	5. Choose 'Per Mode' as 'Per Game'
		Select dropdownPerMode = new Select(driver.findElement(By.xpath("//label/p[text()='Per Mode']/following::select[1]")));
		dropdownPerMode.selectByValue("PerGame");
		
//	6. Choose 'Season Segment' as 'Last Game'
		Select dropdownSeasonSegment = new Select(driver.findElement(By.xpath("//label/p[text()='Season Segment']/following::select[1]")));
		dropdownSeasonSegment.selectByValue("LastNGames=1");
		Thread.sleep(15000);
		
//	7. Click on the player name with lowest age
//	8. Click on the Profile
		selectPlayerWithLowestAge();
		Thread.sleep(15000);
		handleAndCloseBanner();
		
//	9. Get the Experience of the player
//	10. Verify the player experience as 1.
	    String playerExp = driver.findElement(By.xpath("(//p[text()='EXPERIENCE']/following-sibling::p)[1]")).getText();
	    if(playerExp.equals("1 Year")) {
	    	System.out.println("Test Passed");
	    }
	    else
	    {
	    	System.out.println("Test Failed");
	    }		
	}
	
	public void handleAndCloseBanner()
	{
		try {
			wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//div[@class='bx-align']/div"))));
			wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//div[text()='Click to subscribe to League Pass']/following::button[1]"))));
			driver.findElement(By.xpath("//div[text()='Click to subscribe to League Pass']/following::button[1]")).click();			
			System.out.println("Banner is closed");
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public void selectPlayerWithLowestAge()
	{
		List<WebElement> ageList = driver.findElements(By.xpath("(//table)[3]/tbody/tr/td[4]"));
		Map<Integer, WebElement> maps = new HashMap<>();
		for(int i =1; i<=ageList.size(); i++) 
		{				
			String ageString = driver.findElement(By.xpath("((//table)[3]/tbody/tr/td[4])["+i+"]")).getText();
			int ageInt = Integer.parseInt(ageString);
			maps.put(ageInt , driver.findElement(By.xpath("((//table)[3]/tbody/tr/td[2]/a)["+i+"]")));
		}		
		int minKey = Collections.min(maps.keySet());
        WebElement minElement = maps.get(minKey);
        minElement.click();	
	}
	
}
