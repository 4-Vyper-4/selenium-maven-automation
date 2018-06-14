package com.dice;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DiceJobSearch {

	@SuppressWarnings("resource")
	public static void main(String[] args) {

		// maven webdriver depend setup
		WebDriverManager.chromedriver().setup();
		// create driver object
		WebDriver driver = new ChromeDriver();

		// fullscreen
		driver.manage().window().fullscreen();

		// set wait time, universally
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);

		// open webpage
		driver.get("https://www.dice.com/");

		// check title
		String expected = "Job Search for Technology Professionals | Dice.com";
		String actual = driver.getTitle();
		String holder = new String();

		if (actual.equals(expected))
			System.out.println("Pass");
		else {
			System.out.println("Fail");
			System.out.println("Expected: " + expected);
			System.out.println("Actual: " + actual);
			throw new RuntimeException("Step FAIL: dice hompage did not load succesfully");
		}

		String titles = "C:\\Users\\Vyper\\Documents\\Maven\\selenium-maven-automation\\job_titles.txt";
		String line = null;
		ArrayList<String> tl = new ArrayList<>();
		ArrayList<String> tlResult = new ArrayList<>();

		try {
			FileReader fileReader = new FileReader(titles);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			while ((line = bufferedReader.readLine()) != null) {
				tl.add(line);
			}
		} catch (FileNotFoundException e) {
			System.out.println("Unable to open file '" + titles + "'");
			e.printStackTrace();
		} catch (IOException ex) {
			System.out.println("Error reading file '" + titles + "'");

		}

		for(int i = 0; i < tl.size(); i++) {
		// create object of word we search
		String keyword = (String) tl.get(i);
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

		if (countResult > 0) {
			System.out
					.println("PASS: Keyword " + keyword + " search results are " + countResult + " in location " + zip);
		holder = keyword.concat((" "+countResult));
		}else {
			System.out
					.println("FAIL: Keyword " + keyword + " search results are " + countResult + " in location " + zip);
		continue;
		}
		tlResult.add(holder);
		driver.navigate().back();
		
		}
		
		
		driver.close();
		System.out.println(tlResult);
		
		PrintWriter writer;
		try {
			writer = new PrintWriter("resultsofrun.txt", "UTF-8");
			writer.println(tlResult);			
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}

}
