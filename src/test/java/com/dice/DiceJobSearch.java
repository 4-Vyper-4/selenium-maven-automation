package com.dice;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DiceJobSearch {

	public static void main(String[] args) {

		// maven webdriver depend setup
		WebDriverManager.chromedriver().setup();
		// create driver object
		WebDriver driver = new ChromeDriver();

		// fullscreen
		driver.manage().window().fullscreen();

		// set wait time, universally
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

		// open webpage
		driver.get("https://www.dice.com/");

		// check title
		String expected = "Job Search for Technology Professionals | Dice.com";
		String actual = driver.getTitle();

		if (actual.equals(expected))
			System.out.println("Pass");
		else {
			System.out.println("Fail");
			System.out.println("Expected: " + expected);
			System.out.println("Actual: " + actual);
			throw new RuntimeException("Step FAIL: dice hompage did not load succesfully");
		}

		// create object of word we search
		String keyword = "sdet";
		// locate search element of search //send key to input text
		driver.findElement(By.id("search-field-keyword")).clear();
		driver.findElement(By.id("search-field-keyword")).sendKeys(keyword);

		String zip = "22030";
		// second search element of zip
		driver.findElement(By.id("search-field-location")).clear();
		driver.findElement(By.id("search-field-location")).sendKeys(zip);

		// click search format
		driver.findElement(By.id("findTechJobs")).click();

		// check if count results
		String count = driver.findElement(By.id("posiCountId")).getText();
		System.out.println(count);

		// to check count more than one
		int countResult = Integer.parseInt(count.replace(",", ""));

		if (countResult > 0)
			System.out
					.println("PASS: Keyword " + keyword + " search results are " + countResult + " in location " + zip);
		else {
			System.out
					.println("FAIL: Keyword " + keyword + " search results are " + countResult + " in location " + zip);
		}

		driver.close();
	}

}
