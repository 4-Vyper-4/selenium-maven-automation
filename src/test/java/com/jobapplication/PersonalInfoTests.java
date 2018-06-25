package com.jobapplication;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.omg.CORBA.portable.ValueOutputStream;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import static org.testng.Assert.*;

import io.github.bonigarcia.wdm.WebDriverManager;

public class PersonalInfoTests {
	WebDriver driver;
	String firstName;
	String lastName;
	int gender;
	String dateOfBirth;
	String email;
	String phoneNumber;
	String city;
	String state;
	String country;
	int selectInt;
	int annualSalary;
	List<String> technologies;
	int yearsOfExperience;
	String education;
	String github;
	List<String> certifications;
	String additionalSkills;
	Faker data = new Faker();
	Random random = new Random();
	String google = "https://google.com";
	boolean gen;
	int genHolder;
	int tmp;
	String dateOfBirthHolder;
	String phoneHolder;
	String cityHolder;
	String stateHolder;
	String countryHolder;
	Integer annHolder;
	boolean skills;
	int yoeHolder;
	int edHolder;
	String edStrHolder;
	boolean edB;
	boolean certsB;
	
	
	@BeforeClass //runs once for all tests
	public void setUp() {
		System.out.println("Setting up WebDriver in BeforeClass...");
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().fullscreen();
		driver.get("https://forms.zohopublic.com/murodil/form/JobApplicationForm/formperma/kOqgtfkv1dMJ4Df6k4_mekBNfNLIconAHvfdIk3CJSQ");
		
	}
	
	@AfterClass
	public void tearDown() {
		driver.close();
	}
	
	
	
	
	@BeforeMethod //runs before each @Test
	public void navigateToHomePage() {
//		System.out.println("Navigating to homepage in @BeforeMethod....");
//		driver.get("https://forms.zohopublic.com/murodil/form/JobApplicationForm/formperma/kOqgtfkv1dMJ4Df6k4_mekBNfNLIconAHvfdIk3CJSQ");	
		firstName = data.name().firstName();
		lastName = data.name().lastName();
		gender = data.number().numberBetween(1,3);
		dateOfBirth = data.date().birthday().toString();
		email = "cevid@shinnemo.com";
		phoneNumber = data.phoneNumber().cellPhone().replace(".", "");
		city=data.address().cityName();
		state=data.address().stateAbbr();
		country=data.address().country();
		annualSalary=data.number().numberBetween(60000, 150000);
		technologies = new ArrayList<>();
		technologies.add("Java-" + data.number().numberBetween(1,4));
		technologies.add("HTML-" + data.number().numberBetween(1,4));
		technologies.add("Selenium WebDriver-" + data.number().numberBetween(1,4));
		technologies.add("TestNG-"+ data.number().numberBetween(1,4));
		technologies.add("Git-"+ data.number().numberBetween(1,4));
		technologies.add("Maven-"+ data.number().numberBetween(1,4));
		technologies.add("JUnit-"+ data.number().numberBetween(1,4));
		technologies.add("Cucumber-"+ data.number().numberBetween(1,4));
		technologies.add("API Automation-"+ data.number().numberBetween(1,4));
		technologies.add("JDBC-"+ data.number().numberBetween(1,4));
		technologies.add("SQL-"+ data.number().numberBetween(1,4));
		
		yearsOfExperience = data.number().numberBetween(1, 11);
		education = data.number().numberBetween(1, 4)+"";
		github = "https://github.com/CybertekSchool/selenium-maven-automation.git";
		certifications = new ArrayList<>();
		certifications.add("Java OCA");
		certifications.add("AWS");
		additionalSkills = data.job().keySkills();
		
	}
	
	@Test
	public void submitFullApplication() {
		driver.findElement(By.xpath("//input[@name='Name_First']")).sendKeys(firstName);
		driver.findElement(By.xpath("//input[@name='Name_Last']")).sendKeys(lastName);
		setGender(gender);
		setDateOfBirth(dateOfBirth);
		driver.findElement(By.xpath("//input[@name='Email']")).sendKeys(email);
		driver.findElement(By.xpath("//input[@name='countrycode']")).sendKeys(phoneNumber);
		phoneHolder = phoneNumber;
		driver.findElement(By.xpath("//input[@name='Address_City']")).sendKeys(city);
		cityHolder = city;
		driver.findElement(By.xpath("//input[@name='Address_Region']")).sendKeys(state);
		stateHolder = state;
		Select countryElem = new Select(driver.findElement(By.xpath("//select[@id='Address_Country']")));
		selectInt = data.number().numberBetween(1, countryElem.getOptions().size());
		System.out.println(selectInt);
		countryElem.selectByIndex(selectInt);
		System.out.println(selectInt);
		countryHolder = driver.findElement(By.xpath(("//select[@name = 'Address_Country']/option["+(selectInt+1)+"]"))).getText();
		System.out.println(countryHolder);
		driver.findElement(By.xpath("//input[@name='Number']")).sendKeys(String.valueOf(annualSalary)+Keys.TAB);
		annHolder = annualSalary;
		verifySalaryCalculations(annualSalary);
		driver.findElement(By.xpath("//em[.=' Next ']")).click();
		
		// SECOND PAGE ACTIONS
		setSkillset(technologies);
		if(yearsOfExperience > 0) {
			driver.findElement(By.xpath("//a[@rating_value='"+ yearsOfExperience +"']")).click();
		}
		yoeHolder = yearsOfExperience;
		Select educationList = new Select(driver.findElement(By.xpath("//select[@name='Dropdown']")));
		
		edHolder = data.number().numberBetween(1, educationList.getOptions().size());
		educationList.selectByIndex(edHolder);
		setGitHub(github);
		setCerts(certifications);
		driver.findElement(By.xpath("//button[@elname ='submit']")).click();
		
	}
	
	public void setSkillset(List<String> tech) {
		
		for (String skill : tech) {
			String technology = skill.substring(0, skill.length()-2);
			int rate = Integer.parseInt(skill.substring(skill.length()-1));
			
			String level = "";
			
			switch(rate) {
				case 1:
					level = "Expert";
					break;
				case 2:
					level = "Proficient";
					break;
				case 3:
					level = "Beginner";
					break;
				default:
					fail(rate + " is not a valid level");
			}
			
			String xpath = "//input[@rowvalue='"+ technology +"' and @columnvalue='"+ level +"']";
			driver.findElement(By.xpath(xpath)).click();
			
		}
		
		
	}
	
	public void verifySalaryCalculations(int annual) {
		String monthly = driver.findElement(By.xpath("//input[@name='Formula']")).getAttribute("value");
		String weekly = driver.findElement(By.xpath("//input[@name='Formula1']")).getAttribute("value");
		String hourly =  driver.findElement(By.xpath("//input[@name='Formula2']")).getAttribute("value");
		
		System.out.println(monthly);
		System.out.println(weekly);
		System.out.println(hourly);
		
		DecimalFormat formatter = new DecimalFormat("#.##");
		
		assertEquals(Double.parseDouble(monthly),Double.parseDouble(formatter.format((double)annual /12.0)));
		assertEquals(Double.parseDouble(weekly),Double.parseDouble(formatter.format((double)annual / 52.0)));
		assertEquals(Double.parseDouble(hourly),Double.parseDouble(formatter.format((double)annual / 52.0 / 40.0)));
	}
	
	public void setDateOfBirth(String bday) {
		String[] pieces = bday.split(" ");
		String birthDay = pieces[2] + "-" +  pieces[1] + "-" + pieces[5];
		dateOfBirthHolder = birthDay;
		driver.findElement(By.xpath("//input[@id='Date-date']")).sendKeys(birthDay);
	}
	public void setGender(int n) {
		if(n==1) {
			driver.findElement(By.xpath("//input[@value='Male']")).click();
			genHolder = 1;
		}else {
			driver.findElement(By.xpath("//input[@value='Female']")).click();
			genHolder = 2;
		}
		
	}
	
	@Test
	public void fullNameEmptyTest() {
		//firstly assert that you are on the correct page
		assertEquals(driver.getTitle(), "SDET Job Application");
		
		driver.findElement(By.xpath("//input[@elname='first']")).clear();	
		driver.findElement(By.xpath("//*[@elname='last']")).clear();

		driver.findElement(By.xpath("//em[.=' Next ']")).click();

		String nameError = driver.findElement(By.xpath("//p[@id='error-Name']")).getText();
		assertEquals(nameError, "Enter a value for this field.");
	}
	public void setGitHub(String github) {
		driver.findElement(By.xpath("//input[@name = 'Website']")).sendKeys(github);
	}
	public void setCerts(List<String> certs) {
		for(String str : certs) {
			if(str.toLowerCase().equals((driver.findElement(By.xpath("//input[@type = 'checkbox' and @id='Checkbox_1']")).getAttribute("value")).toLowerCase()))
			driver.findElement(By.xpath("//input[@type = 'checkbox' and @id='Checkbox_1']")).click();
			else if(str.toLowerCase().equals((driver.findElement(By.xpath("//input[@type = 'checkbox' and @id='Checkbox_2']")).getAttribute("value")).toLowerCase()))
			driver.findElement(By.xpath("//input[@type = 'checkbox' and @id='Checkbox_2']")).click();	
			else if(str.toLowerCase().equals((driver.findElement(By.xpath("//input[@type = 'checkbox' and @id='Checkbox_3']")).getAttribute("value")).toLowerCase()))
			driver.findElement(By.xpath("//input[@type = 'checkbox' and @id='Checkbox_3']")).click();
			else continue;
		}
	}
	
	@Test
	public void yCheckForm() {
		String actgen = driver.findElement(By.xpath("//label[@class='descFld']/div[9]")).getText().toLowerCase();
		if(actgen.contains("female")) tmp = 2;
		else tmp = 1;
		if(tmp==genHolder) gen = true;
		else gen = false;
		
		Assert.assertTrue(gen);
		String dob = driver.findElement(By.xpath("//label[@class='descFld']/div[10]")).getText();
		Assert.assertTrue(dob.contains(dateOfBirthHolder));
		String em = driver.findElement(By.xpath("//label[@class='descFld']/div[11]")).getText();
		Assert.assertTrue(em.contains(email));
		String ph = driver.findElement(By.xpath("//label[@class='descFld']/div[12]")).getText();
		Assert.assertTrue(ph.contains(phoneHolder));
		String addy = driver.findElement(By.xpath("//label[@class='descFld']/div[13]")).getText().toLowerCase();
		Assert.assertTrue((addy.contains(cityHolder.toLowerCase())&&(addy.contains(stateHolder.toLowerCase()))));
		System.out.println(addy);
		System.out.println(countryHolder);
		Assert.assertTrue((addy.contains(countryHolder.toLowerCase())));
		String annS = driver.findElement(By.xpath("//label[@class='descFld']/div[14]")).getText().toLowerCase();
		Assert.assertTrue(annS.contains(annHolder.toString()));
		String skill = driver.findElement(By.xpath("//label[@class='descFld']/div[15]")).getText().toLowerCase();
		for(String ski : technologies) {
			ski = ski.replaceAll("-", "");
			ski = ski.replaceAll("[0-9]", "");
			ski = ski.toLowerCase();
			if(skill.contains(ski)) skills = true;
		}
		Assert.assertTrue(skills);
		String yoe = driver.findElement(By.xpath("//label[@class='descFld']/div[16]")).getText();
		Assert.assertTrue(yoe.contains(Integer.valueOf(yoeHolder).toString()));
		edStrHolder = driver.findElement(By.xpath("//label[@class ='descFld']/div[17]")).getText();
		if(edHolder == 1) {
			if(edStrHolder.contains("High School")) edB = true;
		}
		else if(edHolder == 2) {
			if(edStrHolder.contains("Under Graduate")) edB = true;
		}else if(edHolder ==3) {
			if(edStrHolder.contains("Post Graduate")) edB = true;
		}else edB = false;
		Assert.assertTrue(edB);
		String gitTmp = driver.findElement(By.xpath("//label[@class ='descFld']/div[18]")).getText();
		Assert.assertTrue((gitTmp.contains(github)));
		String certTmp = driver.findElement(By.xpath("//label[@class ='descFld']/div[19]")).getText();
		for(String c : certifications) {
			if(certTmp.contains(c)) certsB = true;
			else certsB = false;
		}
		Assert.assertTrue(certsB);
		
		
		
	}
	@Test
	public void zCheckIP()  {
		
		String expectedIP = driver.findElement(By.xpath("//label[@class='descFld']/div[6]")).getText();
		driver.get(google);
		driver.findElement(By.xpath("//input[@title = 'Search']")).sendKeys("What is my IP"+ Keys.ENTER);
		String actualIP = driver.findElement(By.xpath("//div[@class = 'pIpgAc xyYs1c XO51F xsLG9d']")).getText();
		Assert.assertTrue((expectedIP.contains(actualIP)));		
	}
}	
