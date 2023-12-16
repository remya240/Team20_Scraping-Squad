package allComorbidities_recepies;

import testBase.*;
import testBase.BaseClass;
import utilities.WriteExcel;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import utilities.*;
import java.util.*;

public class PaginationAndClick extends BaseClass {

	@Test
	public void allrecipe () throws InterruptedException
	{
		String xpathstralpha;
		WebElementObjects eliminateObjects = new WebElementObjects();
		driver.findElement(By.xpath("//a[@href='RecipeAtoZ.aspx']")).click(); 
		Thread.sleep(1000);
		for(int i = 3; i <=5; i++)  // Recipe Pagination A(3) to Z(53) 
		{
			if (i>= 5) // 
			{

				xpathstralpha = "//div/div[1]/div[1]/table[1]/tbody/tr/td["+i+"]";
				driver.findElement(By.xpath(xpathstralpha)).click();
			} 
			for(int j = 1; j <=3; j++)  // Recipe Pagination 1 to 100000000
			{
				if(i%2!=0 && j>1)
				{

					String xpathstr="//div[1]/div[2]/a["+j+"][@class='respglink' and text()='"+j+"']";
					driver.findElement(By.xpath(xpathstr)).click();
					driver.findElement(By.xpath("(//span[1]/a)[1]")).click();
					if (eliminateObjects.equals(eliminateObjects.eliminateLemonGrass)) {
						driver.navigate().back();
					} else {
						WebElement recipeTitle = driver.findElement(By.xpath("//span[@id= 'ctl00_cntrightpanel_lblRecipeName']"));
						System.out.println(recipeTitle.getText());
						Thread.sleep(2000);
						
						//preparation time
						WebElement preprationtime = driver.findElement(By.xpath("//div/section/p[2]/time[1]"));
						((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView(true);", preprationtime);
						System.out.println("Preparation Time:"+" "+preprationtime.getText());
						
						//cooking time
						WebElement cookingtime = driver.findElement(By.xpath("//div/section/p[2]/time[2]"));
						System.out.println("Cooking Time:"+" "+cookingtime.getText());
						
						//Preparation Method
						WebElement prepmethod = driver.findElement(By.xpath("//div[11]/section/div/div[1]"));
						String strValue = prepmethod.getText();
						if(strValue.contains("tsp") ||strValue.contains("tbsp") )
						{
							WebElement prepmethodanotherdiv = driver.findElement(By.xpath("//div[12]/section/div/div[1]"));
							System.out.println("Preparation Method:"+" "+prepmethodanotherdiv.getText());
						}
						else
						{ 
							System.out.println("Preparation Method:"+" "+prepmethod.getText());

						}

						// print`Ingredients list
						WebElement ingrendientlistA = driver.findElement(By.xpath("//div[11]/section/div"));
						System.out.println("Ingredients List:"+" "+ingrendientlistA.getText());
						String IngrstrValue = ingrendientlistA.getText();
						if(IngrstrValue.contains("tsp") ||IngrstrValue.contains("tbsp") )
						{

							System.out.println("Ingredients List:"+" "+ingrendientlistA.getText());
						}
						else
						{ 
							WebElement ingrendientlistB = driver.findElement(By.xpath("//div[10]/section/div")); 
							System.out.println("Ingredients List:"+" "+ingrendientlistB.getText());

						}
						//Nutrients value of recipe

						List<WebElement> rows = driver.findElements(By.xpath("//table[@id='rcpnutrients']//tr"));
						int colWidth = 2;
						System.out.printf("Nutrient Values (Abbrv) per piece");
						for (WebElement row : rows) {
							List<WebElement> cells = row.findElements(By.tagName("td"));
							for (int k = 0; k < cells.size(); k++) {
								WebElement cell = cells.get(k);
								String cellText = cell.getText().trim();
								
								System.out.printf("%-" + colWidth + "s", cellText);
							}
							System.out.println();

						}
						
						//Recipe Current URL
						String strUrl = driver.getCurrentUrl();
						System.out.println("Recipe Url is:"+ strUrl);

						driver.navigate().back();
						Thread.sleep(1000);
						WebElement receipeid = driver.findElement(By.xpath("//div[3]/div[1]/div[2]/span[normalize-space(contains (text(),'Recipe#')) and not(@br)]"));
						System.out.println(receipeid.getText());

					} 

				}

			}
		}
	}

}




