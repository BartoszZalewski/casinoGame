package com.casinoGame.casinoGame;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.io.File;
import java.io.IOException;

@SpringBootApplication
public class CasinoGameApplication {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(CasinoGameApplication.class, args);
		File chromeDriverFile = new File("../casinoGame/src/main/resources/chromedriver.exe");
		System.setProperty("webdriver.chrome.driver",chromeDriverFile.getCanonicalPath());
		WebDriver driver = new ChromeDriver();
		driver.get("http://localhost:8080/");

	}
}
