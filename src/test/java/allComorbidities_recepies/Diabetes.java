package allComorbidities_recepies;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class Diabetes extends testBase.BaseClass  {
    //static WebDriver driver;
    /*List<String> eliminators = Arrays.asList(new String[]{"Processed grains - cream of rice", "rice flour", "rice rava", "corn White rice", "White bread", "Pasta", "Sweetened beverages - soda", "flavoured water", "Gatorade", 
              "Fruit Juice -Apple Juice", "orange juice", "pomegranate juice", "Trans fats found in margarines", "peanut butter", "spreads", "frozen foods", "Flavoured curd/yogurt",
              "Sweetened breakfast cereals-corn flakes", "puffed rice", "bran flakes"," instant oatmeal", "Honey", "Maple syrup", "Jaggery",
              "Sweets", "Candies", "Chocolates", "Alcoholic beverages", "Processed meat-Bacon,sausages","hot dos", "deli meats","chicken nuggets","chciken patties","bacon",
              "Jams", "Jelly", "Pickled food - mango", "cucumber, tomatoes","Canned fruits/vegetables -  pineapple", "peaches", "mangos", "pear", "mixed fruit", "mandarine oranges", "cherries",
              "Chips"});*/


    @Test
    public void firststep() throws InterruptedException {

	    driver.findElement(By.xpath("(//a[text()='Recipe A To Z'])[1]")).click();
	    Thread.sleep(2000);
	    
	    
	    
	    
	    
	    
	    
		int rowCounter = 1;
		List<String> pageBeginsWithList = Arrays.asList(new String[] { "0-9", "A", "B", "C", "D", "E", "F", "G", "H",
				"I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" });

		for (int k = 0; k < pageBeginsWithList.size(); k++) {
		//
			
			driver.navigate().to("https://www.tarladalal.com/RecipeAtoZ.aspx?beginswith=" + pageBeginsWithList.get(k));
			int lastPage = 0;
		try {
				lastPage = Integer
						.parseInt(driver.findElement(By.xpath("//div/a[@class= 'respglink'][last()]")).getText());
			} catch (Exception e) {
				// do nothing or log exception
			}
			if (0 != lastPage) {
				for (int j = 1; j <= lastPage; j++) {
					int pageindex = j;
					driver.navigate().to("https://www.tarladalal.com/RecipeAtoZ.aspx?beginswith="
							+ pageBeginsWithList.get(k) + "&pageindex=" + j);
					List<WebElement> recipeCardElements = driver
							.findElements(By.xpath("//div[@class='rcc_recipecard']"));
		
							try {
								WebElement recipeName = driver
										.findElement(By.xpath("//span[@class='rcc_recipename']/a"));
								System.out.print(recipeName.getDomAttribute("href"));
								//writeOutput.setCellData("Diabetes", rowCounter, 1, recipeName.getText());

							} catch (Exception e) {

							}
						
							}

							
							}
							
			}

    }
    }


	
	    
	    
	    
 
	