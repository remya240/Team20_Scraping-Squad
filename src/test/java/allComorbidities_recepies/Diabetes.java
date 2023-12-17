package allComorbidities_recepies;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import utilities.ConfigReader;
import utilities.WriteExcel;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.time.Duration;

public class Diabetes extends testBase.BaseClass {
	private static final Logger log = LogManager.getLogger(Diabetes.class);

	@Test
	public void fetchRecipeDetails() throws InterruptedException, Exception {
		ConfigReader.loadConfig();
		String strEliminators = ConfigReader.getEliminatorList();
		String[] arrEliminators = strEliminators.split(",");

		List<String> DiabetesEliminators = Arrays.asList(arrEliminators);
		/*
		 * List<String> eliminators = Arrays.asList(new String[] {
		 * "Processed grains - cream of rice", "rice flour", "rice rava",
		 * "corn White rice", "White bread", "Pasta", "Sweetened beverages - soda",
		 * "flavoured water", "Gatorade", "Fruit Juice -Apple Juice", "orange juice",
		 * "pomegranate juice", "Trans fats found in margarines", "peanut butter",
		 * "spreads", "frozen foods", "Flavoured curd/yogurt",
		 * "Sweetened breakfast cereals-corn flakes", "puffed rice", "bran flakes",
		 * " instant oatmeal", "Honey", "Maple syrup", "Jaggery", "Sweets", "Candies",
		 * "Chocolates", "Alcoholic beverages", "Processed meat-Bacon,sausages",
		 * "hot dos", "deli meats", "chicken nuggets", "chciken patties", "bacon",
		 * "Jams", "Jelly", "Pickled food - mango", "cucumber, tomatoes",
		 * "Canned fruits/vegetables -  pineapple", "peaches", "mangos", "pear",
		 * "mixed fruit", "mandarine oranges", "cherries", "Chips" });
		 */
		// WebDriverWait wait = new WebDriverWait(driver,
		// java.time.Duration.ofSeconds(10));
		// WebElement recipeAToZLink =
		// wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//a[text()='Recipe
		// A To Z'])[1]")));
		// String xpathExpression = "(//a[text()='Recipe A To Z'])[1]";
		// WebElement recipeAToZLink =
		// wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathExpression)));
		// recipeAToZLink.click();
		// driver.findElement(By.xpath("(//a[text()='Recipe A To Z'])[1]")).click();
		// Thread.sleep(2000);
		driver.findElement(By.xpath("//div/a[text()= 'Recipe A To Z']")).click();
		Thread.sleep(2000);
		int rowCounter = 1;
		// List<String> categoriesToInclude = Arrays.asList("Breakfast", "Lunch",
		// "Snack", "Dinner");

		// Write to Excel
//		String OUTPUT_DATA_FILE_PATH = "C:\\Users\\ajith\\Desktop\\workspace\\Team20_ScrapingSquad_Recepiescraping\\src\\test\\resources\\outputDataExcel\\outputDataExcel.xlsx";
		WriteExcel writeOutput = new WriteExcel();

		writeOutput.setCellData("Diabetes", 0, 0, "Recipe ID");
		writeOutput.setCellData("Diabetes", 0, 1, "Recipe Name");
		writeOutput.setCellData("Diabetes", 0, 2, "Recipe Category(Breakfast/lunch/snack/dinner)");
		writeOutput.setCellData("Diabetes", 0, 3, "Food Category(Veg/non-veg/vegan/Jain");
		writeOutput.setCellData("Diabetes", 0, 4, "Ingredients");
		writeOutput.setCellData("Diabetes", 0, 5, "Preparation Time");
		writeOutput.setCellData("Diabetes", 0, 6, "Cooking Time");
		writeOutput.setCellData("Diabetes", 0, 7, "Preparation method");
		writeOutput.setCellData("Diabetes", 0, 8, "Nutrient values");
		writeOutput.setCellData("Diabetes", 0, 9, "Recipe URL");
		// writeOutput.setCellData("Diabetes", 0, 10, "Recipe URL");
		// Map<String, String> recipeIdUrls = new HashMap<>();
		/// int rowCounter = 1;
		// run in a loop for all recipe in a page
		List<String> pageBeginsWithList = Arrays.asList(new String[] { "0-9", "A", "B", "C", "D", "E", "F", "G", "H",
				"I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" });
		for (int k = 0; k < pageBeginsWithList.size(); k++) {
			driver.navigate().to("https://www.tarladalal.com/RecipeAtoZ.aspx?beginswith=" + pageBeginsWithList.get(k));
			int lastPage = 0;
			try {
				lastPage = Integer
						.parseInt(driver.findElement(By.xpath("//div/a[@class= 'respglink'][last()]")).getText());
				lastPage = 2; // This needs to remove for all the pages
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
					List<String> recipeUrls = new ArrayList<>();
					Map<String, String> recipeIdUrls = new HashMap<>();
					recipeCardElements.stream().forEach(recipeCardElement -> {
						recipeUrls.add("https://www.tarladalal.com/" + recipeCardElement
								.findElement(By.xpath("//span[@class='rcc_recipename']/a")).getDomAttribute("href"));
						// example: recipeIdUrls.put("id","url");=> Extracted Recipe Id and Recipe URL
						// Here and added to a hashmap
						recipeIdUrls.put(recipeCardElement.getDomAttribute("id").replace("rcp", ""),
								"https://www.tarladalal.com/"
										+ recipeCardElement.findElement(By.tagName("a")).getDomAttribute("href"));
					});
					// String recipeName = "";

					for (Map.Entry<String, String> recipeIdUrlEntry : recipeIdUrls.entrySet()) {
						String recipeUrl = recipeIdUrlEntry.getValue();
						String recipeId = recipeIdUrlEntry.getKey();
						// WebElement recipeNameElement = recipeCard
						// .findElement(By.xpath(".//span[@class='rcc_recipename']/a"));
						// String recipeUrl = recipeIdUrlEntry.getValue();
						// Extract Recipe ID from the URL (Assuming it's the last part after '/')
						// String recipeId = recipeIdUrlEntry.getKey();
						driver.navigate().to(recipeUrl);
						driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

						if (isEliminated(DiabetesEliminators)) {
							// driver.navigate().to("//div/a[text()= 'Recipe A To Z']");
						} else {
							try {
								log.info("Recipe ID: {}", recipeId);
								writeOutput.setCellData("Diabetes", rowCounter, 0, recipeId);
							} catch (Exception e) {

							}
							try {
								WebElement recipeTitle = driver
										.findElement(By.xpath("//span[@id= 'ctl00_cntrightpanel_lblRecipeName']"));
								System.out.print(recipeTitle.getText());
								writeOutput.setCellData("Diabetes", rowCounter, 1, recipeTitle.getText());
							} catch (Exception e) {

							}
							try {
								WebElement recipeCategory = driver.findElement(By.xpath(
										"//span[@itemprop= 'description']/*[contains (text(), 'breakfast') or contains (text(), 'lunch') or contains (text(), 'dinner')]"));

								log.info("RecipeCategory: {}", recipeCategory.getText());
								writeOutput.setCellData("Diabetes", rowCounter, 2, recipeCategory.getText());
							} catch (Exception e) {

							}
							try {
								WebElement foodCategory = driver
										.findElement(By.xpath("//a/span[text()= 'No Cooking Veg Indian']"));

								log.info("FoodCategory: {}", foodCategory.getText());
								writeOutput.setCellData("Diabetes", rowCounter, 3, foodCategory.getText());
							} catch (Exception e) {
							}
							try {
								WebElement nameOfIngredients = driver.findElement(By.xpath("//div[@id= 'rcpinglist']"));

								log.info("nameOfIngredients: {}", nameOfIngredients.getText());
								writeOutput.setCellData("Diabetes", rowCounter, 4, nameOfIngredients.getText());
							} catch (Exception e) {
							}
							try {
								WebElement preparationTime = driver
										.findElement(By.xpath("//p/time[@itemprop= 'prepTime']"));

								log.info("preparationTime: {}", preparationTime.getText());
								writeOutput.setCellData("Diabetes", rowCounter, 5, preparationTime.getText());
							} catch (Exception e) {
							}
							try {
								WebElement cookTime = driver.findElement(By.xpath("//p/time[@itemprop= 'cookTime']"));

								log.info("cookTime: {}", cookTime.getText());
								writeOutput.setCellData("Diabetes", rowCounter, 6, cookTime.getText());
							} catch (Exception e) {
							}
							try {
								WebElement prepMethod = driver
										.findElement(By.xpath("//div[@id= 'ctl00_cntrightpanel_pnlRcpMethod']"));

								log.info("prepMethod: {}", prepMethod.getText());
								writeOutput.setCellData("Diabetes", rowCounter, 7, prepMethod.getText());
							} catch (Exception e) {
							}
							try {
								WebElement nutrients = driver.findElement(By.xpath("//table[@id= 'rcpnutrients']"));

								log.info("nutrients: {}", nutrients.getText());
								writeOutput.setCellData("Diabetes", rowCounter, 8, nutrients.getText());
							} catch (Exception e) {
							}
							try {
								log.info("nutrients: {}", recipeUrl);
								writeOutput.setCellData("Diabetes", rowCounter, 9, recipeUrl);
							} catch (Exception e) {
							}

							rowCounter++;
						}

					}

				}

			}
		} // Close the workbook after completing all write operations
			// writeOutput.closeWorkbook();

	}

	private boolean isEliminated(List<String> eliminators) {
		AtomicBoolean isEliminatorPresent = new AtomicBoolean(false);

		eliminators.parallelStream().forEach(eliminator -> {
			try {
				WebElement ingredientWebElement = driver.findElement(By.xpath("//div[@id= 'rcpinglist']"));
				String ingredients = ingredientWebElement.getText();
				if (null != ingredients && null != eliminator
						&& ingredients.toLowerCase().contains(eliminator.toLowerCase())) {
					isEliminatorPresent.set(true);
				}
			} catch (Exception e) {
				System.out.print("No Such Element " + e.getLocalizedMessage());
			}
			try {

				WebElement methodWebElement = driver.findElement(By.xpath("//div[@id='recipe_small_steps']"));
				String method = methodWebElement.getText();
				if (null != method && null != eliminator && method.toLowerCase().contains(eliminator.toLowerCase())) {
					isEliminatorPresent.set(true);
				}
			} catch (Exception e) {
				System.out.print("No Such Element " + e.getLocalizedMessage());
			}
		});
		return isEliminatorPresent.get();
	}
}