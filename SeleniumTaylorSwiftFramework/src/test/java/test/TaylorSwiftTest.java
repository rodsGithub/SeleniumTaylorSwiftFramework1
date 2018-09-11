/**
 * @author Rodrigo Castillo
 * @purpose:
 * 
 * 1.	Navigate to http://wikipedia.org/  												 		-> The Wikipedia home page is loaded.
 * 2.	Search for ‘Taylor Swift’ 														     	-> The article for ‘Taylor Swift’ should load.
 * 3.	Validate the ‘Studio albums’ contents and the bottom of the page under ‘ExternalLinks’ 	-> The ‘Studio albums’ section.
 * 		should contain ‘Taylor Swift’, ‘Fearless’, ‘Speak Now’,‘Red’, ’1989’, ‘Reputation’
 * 4.	Validate a hover message is displayed when the ‘Reputation’ link in 
 * 		the ‘Studioalbums’ section is moused over 											    -> The hover message appears.
 *
 */

package test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import config.PropertiesFile;
import pages.WikipediaHomePage;									// Import Page Object for Wikipedia Home Page.

public class TaylorSwiftTest {

	static WebDriver driver = null;							    // Define Webdriver
	public static String browserName = null;					// Define Browser to use from properties file.

	@BeforeTest
	public void setUpTest() {

		String projectPath = System.getProperty("user.dir");    // Use relative address/path
		PropertiesFile.getProperties();

		// Set up browsers:
		if (browserName.equalsIgnoreCase("chrome")) {															   // Chrome Browser.
			System.setProperty("webdriver.chrome.driver", projectPath + "/drivers/chromedriver/chromedriver.exe"); // Set the path for Chrome Driver.
			ChromeOptions chromeOptions = new ChromeOptions();													   // Define Chrome options.
			chromeOptions.addArguments("--start-maximized");													   // Start browser maximized.
			driver = new ChromeDriver(chromeOptions);															   // Browser driver to use.
		}
		else if (browserName.equalsIgnoreCase("firefox")) {														   // Firefox Browser.
			System.setProperty("webdriver.gecko.driver", projectPath + "/drivers/geckodriver/geckodriver.exe");    // Set the path for Firefox Driver.
			driver = new FirefoxDriver();																		   // Browser driver to use.
		}

	}

	@Test
	public static void wikipediaLoad() {											// Load Wikipedia Home Page

		driver.get("http://wikipedia.org/"); 										// Go to Wikipedia.
		if(driver.getPageSource().contains("Wikipedia") && 							// If Page Source contains Wikipedia, and, 
				driver.getPageSource().contains("English") &&
				driver.getPageSource().contains("Español") &&						//  Available...
				driver.getPageSource().contains("Deutsch") &&
				driver.getPageSource().contains("Français") &&						//           ... Languages, and, 
				driver.getPageSource().contains("Italiano") &&
				driver.getPageSource().contains("Português") &&
				driver.getPageSource().contains("Polski") &&
				driver.getCurrentUrl().contains("https://www.wikipedia.org/")) 		// URL is correct, then,
		{
			System.out.println("\n*** Wikipedia Home Page was correctly loaded\n"); // Page was loaded.
		} else {																	// If error;
			System.out.println("\n*** Wikipedia Home Page was NOT loaded\n"); 		// Page was NOT loaded correctly.
		}

	}

	@Test
	public static void wikipediaSearch() throws Exception {								// Search in Wikipedia for specific text.

		String famousPerson = "Taylor Swift";
		String famousPersonFullName = "Taylor Alison Swift";

		String inputText = WikipediaHomePage.searchInput(driver).getAttribute("value"); // Get the text from Textbox, it must be empty.
		if(inputText.equals("")) {
			System.out.println("Input Search Box is initially empty, which is correct");   // Input box is empty.
		} else {
			System.out.println("Input Box must be empty, but it contains : " + inputText); // Input box is NOT empty.
		}

		WikipediaHomePage.searchInput(driver).sendKeys(famousPerson);					// Search for Taylor Swift
		inputText = WikipediaHomePage.searchInput(driver).getAttribute("value");        // Get the text just sent.
		if(inputText.equals(famousPerson)) {
			System.out.println("Input Text was entered correctly : " + inputText);      // Input text is correct.
		} else {
			System.out.println("Input Text was NOT entered correctly : " + inputText);  // Input text is incorrect.
		}


		WikipediaHomePage.searchButton(driver).click();				                           // Click on search button.
		if(driver.getPageSource().contains(famousPerson) && 		    	                   // If Page Source contains Wikipedia, and, 
				driver.getPageSource().contains(famousPersonFullName) &&
				driver.getCurrentUrl().contains("https://en.wikipedia.org/wiki/Taylor_Swift")) // URL is correct, then,
		{
			System.out.println("\n*** The article for ‘Taylor Swift’ was successfully loaded\n"); // Page was successfully loaded.
		} else {														                       // If error;
			System.out.println("\n*** The article for ‘Taylor Swift’ was NOT loaded\n");       // Page was NOT loaded correctly.
		}

		WebElement bottomTable= driver.findElement(By.cssSelector("table.nowraplinks.vcard.hlist.collapsible.expanded.navbox-inner.mw-collapsible.mw-made-collapsible"));   // Locate bottom table under External Links
		WebElement studioAlbumsTitleRow = bottomTable.findElements(By.tagName("tr")).get(2);																				// Locate Studio Album row within the table.
		WebElement studioAlbums = studioAlbumsTitleRow.findElements(By.tagName("td")).get(0);																				// Locate Albums to validate within the row.
		WebElement reputationAlbum = studioAlbums.findElements(By.tagName("li")).get(5);																					// Locate Reputation Album within the albums.
		
		String studioAlbumsRowText = studioAlbums.getText();                                                                                 // Get the albums to validate.

		if((studioAlbumsRowText).contains("Taylor Swift Fearless Speak Now Red 1989 Reputation")) {											 // Validate the ‘Studio albums’ contents
			System.out.println("*** Validation is correct, Studio Albums contain: \"" + studioAlbumsRowText + "\"\n");						 // Validation is correct, show displayed albums
		} else {
			System.out.println("*** Validation FAILED Studio Albums contain other albums than expected: \"" + studioAlbumsRowText + "\"\n"); // Validation failed, show displayed albums.
		}

		
		Actions ToolTip1 = new Actions(driver);													  //   Perform 
		Thread.sleep(2500);																		  //            mouse over
		ToolTip1.clickAndHold(reputationAlbum).perform();										  //                       action
		
		Thread.sleep(2500);																		  // Give some time to the tooltip to be displayed in order to getText
		String ToolTipText = driver.findElement(By.cssSelector(".mwe-popups-extract")).getText(); // Get Text from Tooltip
		Thread.sleep(2000);
		 
		if((ToolTipText).contains("Reputation is the sixth studio album by American singer and songwriter Taylor Swift")) // Validate that the hover message appears.  
		{
			System.out.println("\n*** The hover message appears, which is correct\n"); 									  // Validation is correct.
		} else {																					
			System.out.println("\n*** The hover message is NOT displayed\n"); 											  // Validation failed.
		}
		System.out.println("The Tooltip value is: " + ToolTipText);								  						  // Display Tooltip value.
	}


	@AfterTest
	public void tearDownTest() {

		driver.close();										           // Close Browser.
		driver.quit();										           // Quit Webdriver.
		System.out.println("\n*** Test completed ***\n");   			   // Confirm test was completed.

	}

}
