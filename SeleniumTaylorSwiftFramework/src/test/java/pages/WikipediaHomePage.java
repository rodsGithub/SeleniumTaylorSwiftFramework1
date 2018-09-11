/**
 * @author Rodrigo Castillo
 * 
 *   This class will store all the locators for Wikipedia Home Page.
 *
 */

package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class WikipediaHomePage {
	
	private static WebElement element = null;   				// Define WebElement

	public static WebElement searchInput(WebDriver driver) {
		element = driver.findElement(By.id("searchInput"));		// Wikipedia Input Search Field.
		return element;
	}

	public static WebElement searchButton(WebDriver driver) {
		element = driver.findElement(By.cssSelector("button.pure-button.pure-button-primary-progressive"));  // Search button element.
		return element;
	}

}
