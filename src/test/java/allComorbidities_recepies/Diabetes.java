package allComorbidities_recepies;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import utilities.WriteExcel;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Diabetes extends testBase.BaseClass {

    @Test
    public void fetchRecipeDetails() throws InterruptedException, Exception {
        List<String> eliminators = Arrays.asList(new String[]{"Processed grains - cream of rice", "rice flour",
                "rice rava", "corn White rice", "White bread", "Pasta", "Sweetened beverages - soda", "flavoured water",
                "Gatorade", "Fruit Juice -Apple Juice", "orange juice", "pomegranate juice",
                "Trans fats found in margarines", "peanut butter", "spreads", "frozen foods", "Flavoured curd/yogurt",
                "Sweetened breakfast cereals-corn flakes", "puffed rice", "bran flakes", " instant oatmeal", "Honey",
                "Maple syrup", "Jaggery", "Sweets", "Candies", "Chocolates", "Alcoholic beverages",
                "Processed meat-Bacon,sausages", "hot dos", "deli meats", "chicken nuggets", "chciken patties", "bacon",
                "Jams", "Jelly", "Pickled food - mango", "cucumber, tomatoes", "Canned fruits/vegetables -  pineapple",
                "peaches", "mangos", "pear", "mixed fruit", "mandarine oranges", "cherries", "Chips"});

        driver.findElement(By.xpath("(//a[text()='Recipe A To Z'])[1]")).click();
        Thread.sleep(2000);
        int rowCounter = 1;
        List<String> categoriesToInclude = Arrays.asList("Breakfast", "Lunch", "Snack", "Dinner");

        // Write to Excel
        String OUTPUT_DATA_FILE_PATH = "C:\\Users\\ajith\\Desktop\\workspace\\Team20_ScrapingSquad_Recepiescraping\\src\\test\\resources\\outputDataExcel\\outputDataExcel.xlsx";
        WriteExcel writeOutput = new WriteExcel();

        try {
            writeOutput.setCellData("Diabetes", 0, 0, "Recipe ID");
            writeOutput.setCellData("Diabetes", 0, 1, "Recipe Name");
            writeOutput.setCellData("Diabetes", 0, 2, "Incredients");
            writeOutput.setCellData("Diabetes", 0, 3, "Recepie URL");
            for (int pageIndex = 1; pageIndex <= 3; pageIndex++) {
                driver.navigate().to("https://www.tarladalal.com/RecipeAtoZ.aspx?beginswith=&pageindex=" + pageIndex);

                List<WebElement> recipeCardElements = driver.findElements(By.xpath("//div[@class='rcc_recipecard']"));
                String recipeName = "";

                for (WebElement recipeCard : recipeCardElements) {
                    try {
                        WebElement recipeNameElement = recipeCard
                                .findElement(By.xpath(".//span[@class='rcc_recipename']/a"));
                        recipeName = recipeNameElement.getText();
                        String recipeUrl = recipeNameElement.getAttribute("href");
                        // Extract Recipe ID from the URL (Assuming it's the last part after '/')
                        String recipeId = extractRecipeId(recipeUrl);

                        driver.navigate().to(recipeUrl);
                        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
                        WebElement nameOfIngredients = driver.findElement(By.xpath("//div[@id= 'rcpinglist']"));
                        if (isEliminated(eliminators)) {
                            //driver.navigate().to("//div/a[text()= 'Recipe A To Z']");
                        }  else {
                            try {
                            	System.out.println("Recipe ID: " + recipeId);
                                System.out.println("Recipe Name: " + recipeName);
                                System.out.println("Incredients:" + nameOfIngredients.getText());


                                System.out.println("Recipe URL: " + recipeUrl);
                                
                               

                                writeOutput.setCellData("Diabetes", rowCounter, 0, recipeId);
                                writeOutput.setCellData("Diabetes", rowCounter, 1, recipeName);
                                writeOutput.setCellData("Diabetes", rowCounter, 2, nameOfIngredients.getText());
                                writeOutput.setCellData("Diabetes", rowCounter, 3, recipeUrl);
                                rowCounter++;
                            } catch (Exception e) {
                                // Handle exception if needed
                            }

                        }

                    } catch (Exception e) {
                        // Handle exception if needed
                    }
                }
            }
            // Close the workbook after completing all write operations
          //  writeOutput.closeWorkbook();
        } catch (org.apache.poi.EmptyFileException e) {
            System.out.println("Empty Excel file. Check the Excel file.");
        }
    }

    private String extractRecipeId(String recipeUrl) {
        // Extract numeric part from the URL
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(recipeUrl);
        if (matcher.find()) {
            return matcher.group();
        }
        return "";
    }

    private boolean isEliminated(List<String> eliminators) {
        AtomicBoolean isEliminatorPresent = new AtomicBoolean(false);

        eliminators.parallelStream().forEach(eliminator -> {
            try {
            	  WebElement ingredientWebElement = driver.findElement(By.xpath("//div[@id= 'rcpinglist']"));
                  String ingredients = ingredientWebElement.getText();
                  if (null != ingredients && null != eliminator && ingredients.toLowerCase().contains(eliminator.toLowerCase())) {
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