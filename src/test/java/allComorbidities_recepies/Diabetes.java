package allComorbidities_recepies;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import testBase.BaseClass;
import utilities.ConfigReader;
import utilities.WriteExcel;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class Diabetes extends BaseClass {
	private static final Logger log = LogManager.getLogger(Diabetes.class);
	private WebDriverWait wait;

	@Test
	public void fetchRecipeDetails() throws InterruptedException, Exception {
		ConfigReader.loadConfig();
		String strEliminators = ConfigReader.DiabetesgetEliminatorList();
		String[] arrEliminators = strEliminators.split(",");

		List<String> DiabetesEliminators = Arrays.asList(arrEliminators);
		String strToAddlist = ConfigReader.getToAddListDiabetes();
		String[] arrToAddlist = strToAddlist.split(",");
		List<String> DiabetesToAddlist = Arrays.asList(arrToAddlist);

		driver.findElement(By.xpath("//div/a[text()= 'Recipe A To Z']")).click();
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		int rowCounter = 1;
		WriteExcel writeOutput = new WriteExcel();
		// Create rows header

		writeOutput.setCellData("Diabetes", 0, 0, "Recipe ID");
		writeOutput.setCellData("Diabetes", 0, 1, "Recipe Name");
		writeOutput.setCellData("Diabetes", 0, 2, "Recipe Category(Breakfast/lunch/snack/dinner)");
		writeOutput.setCellData("Diabetes", 0, 3, "8 Category(Veg/non-veg/vegan/Jain");
		writeOutput.setCellData("Diabetes", 0, 4, "Ingredients");
		writeOutput.setCellData("Diabetes", 0, 5, "Preparation Time");
		writeOutput.setCellData("Diabetes", 0, 6, "Cooking Time");
		writeOutput.setCellData("Diabetes", 0, 7, "Preparation method");
		writeOutput.setCellData("Diabetes", 0, 8, "Nutrient values");
		writeOutput.setCellData("Diabetes", 0, 9,
				"Targetted morbid conditions (Diabeties/Hypertension/Hypothyroidism)");
		writeOutput.setCellData("Diabetes", 0, 10, "Recipe URL");

		writeOutput.setCellData("Diabetes", 0, 11, "To Add");

		List<String> pageBeginsWithList = Arrays.asList(new String[] { "0-9", "A", "B", "C", "D", "E", "F", "G", "H",
				"I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" });
		for (int k = 0; k < pageBeginsWithList.size(); k++) {
			driver.navigate().to("https://www.tarladalal.com/RecipeAtoZ.aspx?beginswith=" + pageBeginsWithList.get(k));
			int lastPage = 0;
			try {
				lastPage = Integer
						.parseInt(driver.findElement(By.xpath("//div/a[@class= 'respglink'][last()]")).getText());

			} catch (Exception e) {

			}
			if (0 != lastPage) {
				for (int j = 1; j <= lastPage; j++) {
					int pageindex = j;
					driver.navigate().to("https://www.tarladalal.com/RecipeAtoZ.aspx?beginswith="
							+ pageBeginsWithList.get(k) + "&pageindex=" + j);

					List<WebElement> recipeCardElements = wait.until(ExpectedConditions
							.visibilityOfAllElementsLocatedBy(By.xpath("//div[@class='rcc_recipecard']")));
					List<String> RecepieURL = new ArrayList<>();
					Map<String, String> RecepieIDUrls = new HashMap<>();
					recipeCardElements.stream().forEach(recipeCardElement -> {
						RecepieURL.add("https://www.tarladalal.com/" + recipeCardElement
								.findElement(By.xpath("//span[@class='rcc_recipename']/a")).getDomAttribute("href"));

						RecepieIDUrls.put(recipeCardElement.getDomAttribute("id").replace("rcp", ""),
								"https://www.tarladalal.com/"
										+ recipeCardElement.findElement(By.tagName("a")).getDomAttribute("href"));
					});

					for (Map.Entry<String, String> RecepieIDUrlEntry : RecepieIDUrls.entrySet()) {
						String recipeUrl = RecepieIDUrlEntry.getValue();
						String recepieID = RecepieIDUrlEntry.getKey();
						driver.navigate().to(recipeUrl);
						driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

						List<List<String>> elliminatedAndGetToAdd = isEliminatedAndGetToAdd(DiabetesEliminators,
								DiabetesToAddlist);
						String strIsEliminatedList = elliminatedAndGetToAdd.get(0).toString();
						String strToAddString = "";

						if (!elliminatedAndGetToAdd.get(1).isEmpty()) {
							strToAddString = elliminatedAndGetToAdd.get(1).toString();
						}
						if (strIsEliminatedList == "true") {
							// driver.navigate().to("//div/a[text()= 'Recipe A To Z']");
						} else {

							// Recipe id
							try {
								log.info("Recipe ID: {}", recepieID);
								writeOutput.setCellData("Diabetes", rowCounter, 0, recepieID);
							} catch (Exception e) {

							}
							try {
								WebElement recipeName = driver
										.findElement(By.xpath("//span[@id= 'ctl00_cntrightpanel_lblRecipeName']"));
								log.info("Recipe Name", recipeName.getText());
								writeOutput.setCellData("Diabetes", rowCounter, 1, recipeName.getText());
							} catch (Exception e) {

							}
							try {
								WebElement recipeCategory = driver.findElement(By.xpath(
										"//span[@itemprop= 'description']/*[contains (text(), 'breakfast') or contains (text(), 'lunch') or contains (text(), 'dinner')]"));

								log.info("RecipeCategory: ", recipeCategory.getText());
								writeOutput.setCellData("Diabetes", rowCounter, 2, recipeCategory.getText());
							} catch (Exception e) {

							}
							try {
								WebElement FoodCategory = driver
										.findElement(By.xpath("//a/span[text()= 'No Cooking Veg Indian']"));

								log.info("FoodCategory: ", FoodCategory.getText());
								writeOutput.setCellData("Diabetes", rowCounter, 3, FoodCategory.getText());
							} catch (Exception e) {
							}
							try {
								WebElement nameOfIngredients = driver.findElement(By.xpath("//div[@id= 'rcpinglist']"));

								log.info("nameOfIngredients: ", nameOfIngredients.getText());
								writeOutput.setCellData("Diabetes", rowCounter, 4, nameOfIngredients.getText());
							} catch (Exception e) {
							}
							try {
								WebElement preparationTime = driver
										.findElement(By.xpath("//p/time[@itemprop= 'prepTime']"));

								log.info("preparationTime: ", preparationTime.getText());
								writeOutput.setCellData("Diabetes", rowCounter, 5, preparationTime.getText());
							} catch (Exception e) {
							}
							try {
								WebElement cookTime = driver.findElement(By.xpath("//p/time[@itemprop= 'cookTime']"));

								log.info("cookTime: ", cookTime.getText());
								writeOutput.setCellData("Diabetes", rowCounter, 6, cookTime.getText());
							} catch (Exception e) {
							}
							try {
								WebElement prepMethod = driver
										.findElement(By.xpath("//div[@id= 'ctl00_cntrightpanel_pnlRcpMethod']"));

								log.info("prepMethod: ", prepMethod.getText());
								writeOutput.setCellData("Diabetes", rowCounter, 7, prepMethod.getText());
							} catch (Exception e) {
							}
							try {
								WebElement nutrients = driver.findElement(By.xpath("//table[@id= 'rcpnutrients']"));

								log.info("nutrients: ", nutrients.getText());
								writeOutput.setCellData("Diabetes", rowCounter, 8, nutrients.getText());
							} catch (Exception e) {
								writeOutput.setCellData("Diabetes", rowCounter, 9, "Diabetes");

							}
							try {
								log.info("Recepieurl: ", recipeUrl);
								writeOutput.setCellData("Diabetes", rowCounter, 10, recipeUrl);
							} catch (Exception e) {
							}
							try {
								log.info("RecepieToAdd: ");

								writeOutput.setCellData("Hypertension", rowCounter, 11, strToAddString);
							} catch (Exception e) {

							}

							rowCounter++;
						}

					}

				}

			}
		}

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

	private List<List<String>> isEliminatedAndGetToAdd(List<String> eliminators, List<String> toAdd) {

		List<List<String>> elliminatedAndGetToAdd = new ArrayList<>();
		// This will be used to list of matched toAdd itesm.
		List<String> isEliminatedList = new ArrayList<String>();
		List<String> toAddMatchedList = new ArrayList<String>();

		eliminators.parallelStream().forEach(eliminator -> {
			try {
				WebElement ingredientWebElement = driver.findElement(By.xpath("//div[@id= 'rcpinglist']"));
				String ingredients = ingredientWebElement.getText();
				if (null != ingredients && null != eliminator
						&& ingredients.toLowerCase().contains(eliminator.toLowerCase())) {
					// eliminated as true;
					isEliminatedList.add("true");
				} else {
					// eliminated as false;
					isEliminatedList.add("false");
					// Get list of ToAdd lists
					List<String> lstIngredientsWords = Arrays.asList(ingredients.split("\\W+"));
					for (String text : toAdd) {
						if (lstIngredientsWords.contains(text)) {
							if (!toAddMatchedList.contains(text)) {
								toAddMatchedList.add(text);
							}
						}
					}
				}

			} catch (Exception e) {
				System.out.print("No Such Element " + e.getLocalizedMessage());
			}
			try {

				WebElement methodWebElement = driver.findElement(By.xpath("//div[@id='recipe_small_steps']"));
				String method = methodWebElement.getText();
				if (null != method && null != eliminator && method.toLowerCase().contains(eliminator.toLowerCase())) {
					// eliminated as true;
					isEliminatedList.add("true");
				} else {
					// eliminated as false;
					isEliminatedList.add("false");
					// Get list of ToAdd lists
					List<String> lstIngredientsWords = Arrays.asList(method.split("\\W+"));
					for (String text : toAdd) {
						if (lstIngredientsWords.contains(text)) {
							if (!toAddMatchedList.contains(text)) {
								toAddMatchedList.add(text);
							}
						}
					}

				}
			} catch (Exception e) {
				System.out.print("No Such Element " + e.getLocalizedMessage());
			}
		});

		elliminatedAndGetToAdd.add(isEliminatedList);
		elliminatedAndGetToAdd.add(toAddMatchedList);

		return elliminatedAndGetToAdd;
	}
}