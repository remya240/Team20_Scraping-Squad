package allComorbidities_recepies;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import testBase.BaseClass;
import utilities.ConfigReader;
import utilities.WriteExcel;

public class Allergy extends BaseClass {


	@Test
	public void allrecipe () throws InterruptedException, IOException
	{
		ConfigReader.loadConfig();
		String strEliminators = ConfigReader.AllergyList();
		 String[] arrEliminators = strEliminators.split(","); 
		
		 List<String> AllergyEliminators = Arrays.asList(arrEliminators);
		driver.findElement(By.xpath("//div/a[text()= 'Recipe A To Z']")).click();
		Thread.sleep(2000);
		 //WriteExcel writeOutput = new WriteExcel();
	        // Create rows header
	        /*writeOutput.setCellData(sheetname, 0, 0, "Recipe ID");
	        writeOutput.setCellData(sheetname, 0, 1, "Recipe Name");
	        writeOutput.setCellData(sheetname, 0, 2, "Recipe Category(Breakfast/lunch/snack/dinner)");
	        writeOutput.setCellData(sheetname, 0, 3, "Food Category(Veg/non-veg/vegan/Jain)");
	        writeOutput.setCellData(sheetname, 0, 4, "Ingredients");
	        writeOutput.setCellData(sheetname, 0, 5, "Preparation Time");
	        writeOutput.setCellData(sheetname, 0, 6, "Cooking Time");
	        writeOutput.setCellData(sheetname, 0, 7, "Preparation method");
	        writeOutput.setCellData(sheetname, 0, 8, "Nutrient values");
	        writeOutput.setCellData(sheetname, 0, 9, "Recipe URL)");*/
	        
		int setCellvalue=1;
		// run in a loop for all recipe in a page
		List< String> pageBeginsWithList = Arrays.asList(new String[]{"0-9","A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"});
		for(int k=0; k < pageBeginsWithList.size(); k++) {
			driver.navigate().to("https://www.tarladalal.com/RecipeAtoZ.aspx?beginswith="+pageBeginsWithList.get(k));
			int lastPage =0;
			try {
				lastPage= Integer.parseInt(driver.findElement(By.xpath("//div/a[@class= 'respglink'][last()]")).getText());
				 lastPage=2; // This needs to remove for all the pages 
			} 
			catch ( Exception e) {
				
			}
			if (0 != lastPage) {
				for (int j = 1; j <= lastPage; j++) {
					int pageindex = j;

					driver.navigate().to("https://www.tarladalal.com/RecipeAtoZ.aspx?beginswith="+pageBeginsWithList.get(k)+"&pageindex=" + j);
					List<WebElement> recipeCardElements = driver.findElements(By.xpath("//div[@class='rcc_recipecard']"));
					List<String> recipeUrls = new ArrayList<>();
					Map<String, String> recipeIdUrls = new HashMap<>();

					
					recipeCardElements.stream().forEach(recipeCardElement -> {
						recipeUrls.add("https://www.tarladalal.com/" + recipeCardElement.findElement(By.xpath("//span[@class='rcc_recipename']/a")).getDomAttribute("href"));
						
						recipeIdUrls.put(recipeCardElement.getDomAttribute("id").replace("rcp",""),"https://www.tarladalal.com/" + recipeCardElement.findElement(By.tagName("a")).getDomAttribute("href"));
					});

					for (Map.Entry<String,String> recipeIdUrlEntry : recipeIdUrls.entrySet())  {
						String recipeUrl = recipeIdUrlEntry.getValue();
						String recipeId = recipeIdUrlEntry.getKey();
						driver.navigate().to(recipeUrl);
						driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);


						for(int i = 0; i < AllergyEliminators.size();i++) {
							//int setCellvalue=1;
							System.out.println("Print Setvalue above for loop elese:"+setCellvalue);
							String sheetname=AllergyEliminators.get(i)+"Allergies";
							System.out.println("Print Setvalue inside for loop elese:"+setCellvalue);
							if (isEliminated(AllergyEliminators.get(i))) {

							}

							else {

								System.out.println("Print Setvalue inside elese:"+setCellvalue);
								WriteExcel writeOutput = new WriteExcel();
								 writeOutput.AllergySetCellData(sheetname, 0, 0, "Recipe ID");
							       writeOutput.AllergySetCellData(sheetname, 0, 1, "Recipe Name");
							        writeOutput.AllergySetCellData(sheetname, 0, 2, "Recipe Category(Breakfast/lunch/snack/dinner)");
							        writeOutput.AllergySetCellData(sheetname, 0, 3, "Food Category(Veg/non-veg/vegan/Jain)");
							        writeOutput.AllergySetCellData(sheetname, 0, 4, "Ingredients");
							        writeOutput.AllergySetCellData(sheetname, 0, 5, "Preparation Time");
							        writeOutput.AllergySetCellData(sheetname, 0, 6, "Cooking Time");
							        writeOutput.AllergySetCellData(sheetname, 0, 7, "Preparation method");
							        writeOutput.AllergySetCellData(sheetname, 0, 8, "Nutrient values");
							        writeOutput.AllergySetCellData(sheetname, 0, 9, "Recipe URL)");
								WebElement recipeTitle = driver.findElement(By.xpath("//span[@id= 'ctl00_cntrightpanel_lblRecipeName']"));
								System.out.println(recipeTitle.getText());
								writeOutput.AllergySetCellData(sheetname, setCellvalue,1, recipeTitle.getText());
								Thread.sleep(2000);

								//preparation time
								try {
									WebElement preprationtime = driver.findElement(By.xpath("//p/time[@itemprop= 'prepTime']"));
									((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView(true);", preprationtime);
									System.out.println("Preparation Time:"+" "+preprationtime.getText());
									writeOutput.AllergySetCellData(sheetname, setCellvalue,5, preprationtime.getText());
								} catch (Exception e) {
									// foodCategory
								}

								// recipeCategory
								try {
									WebElement recipeCategory = driver.findElement(By.xpath("//span[@itemprop= 'description']/*[contains (text(), 'breakfast') or contains (text(), 'lunch') or contains (text(), 'dinner')]"));
									writeOutput.AllergySetCellData(sheetname, setCellvalue, 2, recipeCategory.getText());
									System.out.print(recipeCategory.getText());
								} catch (Exception e) {
									// foodCategory
								}
								try {
									WebElement foodCategory = driver.findElement(By.xpath("//a/span[text() = 'No Cooking Veg Indian']"));
									writeOutput.AllergySetCellData(sheetname, setCellvalue, 3, foodCategory.getText());
									System.out.print(foodCategory.getText());
								} catch (Exception e) {}

								//cooking time
								try {
									WebElement cookingtime = driver.findElement(By.xpath("//p/time[@itemprop= 'cookTime']"));
									System.out.println("Cooking Time:"+" "+cookingtime.getText());
									writeOutput.AllergySetCellData(sheetname, setCellvalue,6, cookingtime.getText());
								} catch (Exception e) {
									// foodCategory
								}

								try {
									//Preparation Method
									WebElement preprationMethod = driver.findElement(By.xpath("//div[@id= 'ctl00_cntrightpanel_pnlRcpMethod']"));
									System.out.println("Preparation Method:"+" "+preprationMethod.getText());
									writeOutput.AllergySetCellData(sheetname, setCellvalue,7, preprationMethod.getText());
								} catch (Exception e) {
									// foodCategory
								}

								// print`Ingredients list
								try {
									WebElement extractIngrendientlist = driver.findElement(By.xpath("//div[@id= 'rcpinglist']"));
									System.out.println("Ingredients List:"+" "+extractIngrendientlist.getText());
									writeOutput.AllergySetCellData(sheetname, setCellvalue,4, extractIngrendientlist.getText());
								} catch (Exception e) {
									// foodCategory
								}
								//Nutrients value of recipe


								try {
									WebElement nutrients = driver.findElement(By.xpath("//table[@id= 'rcpnutrients']"));
									writeOutput.AllergySetCellData(sheetname, setCellvalue, 8, nutrients.getText());
									System.out.print(nutrients.getText());
								} catch (Exception e) {

							}

								//Recipe Current URL
								try {
									String strUrl = driver.getCurrentUrl();
									System.out.println("Recipe Url is:"+ strUrl);
									writeOutput.AllergySetCellData(sheetname, setCellvalue,9, strUrl);

									String regex = "\\d+";
									Pattern pattern = Pattern.compile(regex);
									Matcher matcher = pattern.matcher(strUrl);
									if (matcher.find()) {
										int Receipeid = Integer.parseInt(matcher.group());
										String Receipeidstr = Integer.toString(Receipeid);
										writeOutput.AllergySetCellData(sheetname, setCellvalue,0, Receipeidstr);
									}
								} catch (Exception e) {
									// foodCategory
								}

								//setCellvalue++;
								//driver.navigate().back();
								Thread.sleep(1000);

							}

						}
						System.out.print("Before increment"+setCellvalue);
						setCellvalue++;
						System.out.print("After increment"+setCellvalue);


					}
				}
			}

		}
	}

	private boolean isEliminated(String string) {
		AtomicBoolean isEliminatorPresent = new AtomicBoolean(false);

		
		try {
			WebElement ingredientWebElement = driver.findElement(By.xpath("//div[@id= 'rcpinglist']"));
			String ingredients = ingredientWebElement.getText();
			if (null != ingredients && null != string && ingredients.toLowerCase().contains(string.toLowerCase())) {
				isEliminatorPresent.set(true);
			}
		} catch (Exception e) {
			System.out.print("No Such Element " + e.getLocalizedMessage());
			System.out.print("Hi -  Fail");
		}
		try {

			WebElement methodWebElement = driver.findElement(By.xpath("//div[@id='recipe_small_steps']"));
			String method = methodWebElement.getText();
			if (null != method && null != string && method.toLowerCase().contains(string.toLowerCase())) {
				isEliminatorPresent.set(true);
			}
		} catch (Exception e) {
			System.out.print("No Such Element " + e.getLocalizedMessage());
			System.out.print("Hi -  Fail");
		}
		//    });
		return isEliminatorPresent.get();
	}
}

	
