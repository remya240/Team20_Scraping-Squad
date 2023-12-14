package allComorbidities_recepies;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import java.util.List;

public class Diabetes extends testBase.BaseClass {

    @Test
    public void fetchRecipeDetails() throws InterruptedException {
        driver.findElement(By.xpath("(//a[text()='Recipe A To Z'])[1]")).click();
        Thread.sleep(2000);

        List<String> initialLetters = List.of("0-9", "A", "B", "C", "D", "E", "F", "G", "H",
                "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z");

        int totalRecipesCount = 0; // Counter for total scraped recipes

        for (String letter : initialLetters) {
            driver.navigate().to("https://www.tarladalal.com/RecipeAtoZ.aspx?beginswith=" + letter);

            int lastPageIndex = 0;

            try {
                lastPageIndex = Integer.parseInt(driver.findElement(By.xpath("(//a[@class='respglink'])[last()]")).getText());
            } catch (Exception e) {
                // Handle exception if needed
            }

            if (lastPageIndex != 0) {
                for (int pageIndex = 1; pageIndex <= lastPageIndex; pageIndex++) {
                    driver.navigate().to("https://www.tarladalal.com/RecipeAtoZ.aspx?beginswith="
                            + letter + "&pageindex=" + pageIndex);

                    List<WebElement> recipeCardElements = driver.findElements(By.xpath("//div[@class='rcc_recipecard']"));

                    for (WebElement recipeCard : recipeCardElements) {
                        try {
                            WebElement recipeNameElement = recipeCard.findElement(By.xpath(".//span[@class='rcc_recipename']/a"));
                            String recipeName = recipeNameElement.getText();
                            String recipeUrl = recipeNameElement.getAttribute("href");

                            System.out.println("Recipe Name: " + recipeName);
                            System.out.println("Recipe URL: " + recipeUrl);

                            totalRecipesCount++; // Increment the counter for each scraped recipe
                        } catch (Exception e) {
                            // Handle exception if needed
                        }
                    }
                }
            }
        }

        System.out.println("Total Scraped Recipes Count: " + totalRecipesCount);
    }
}
