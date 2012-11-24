package controllers;

import models.Ingredient;
import models.IngredientAlias;
import models.IngredientCategory;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;

import java.util.List;

public class Application extends Controller {
  
  public static Result index() {
    return ok(index.render("hello, world"));
  }

    public static Result addIngredients() {
        IngredientCategory cat1 = new IngredientCategory(); cat1.name = "cat1"; cat1.save();
        IngredientCategory cat2 = new IngredientCategory(); cat2.name = "cat2"; cat2.save();
        IngredientCategory cat3 = new IngredientCategory(); cat3.name = "cat3"; cat3.save();

        Ingredient ingredient1 = new Ingredient(); ingredient1.name = "ingredient1"; ingredient1.ingredientCategory = cat1; ingredient1.save();
        Ingredient ingredient2 = new Ingredient(); ingredient2.name = "ingredient2"; ingredient2.ingredientCategory = cat1; ingredient2.save();
        Ingredient ingredient3 = new Ingredient(); ingredient3.name = "ingredient3"; ingredient3.ingredientCategory = cat2; ingredient3.save();
        Ingredient ingredient4 = new Ingredient(); ingredient4.name = "ingredient4"; ingredient4.ingredientCategory = cat2; ingredient4.save();
        Ingredient ingredient5 = new Ingredient(); ingredient5.name = "ingredient5"; ingredient5.ingredientCategory = cat3; ingredient5.save();
        Ingredient ingredient6 = new Ingredient(); ingredient6.name = "ingredient6"; ingredient6.ingredientCategory = cat3; ingredient6.save();

        IngredientAlias alias1 = new IngredientAlias(); alias1.name = "ingredient1a"; alias1.ingredient = ingredient1; alias1.save();
        IngredientAlias alias2 = new IngredientAlias(); alias2.name = "ingredient3a"; alias2.ingredient = ingredient3; alias2.save();
        IngredientAlias alias3 = new IngredientAlias(); alias3.name = "ingredient5a"; alias3.ingredient = ingredient5; alias3.save();

        return ok(index.render("Added new Ingredients"));
    }

    public static Result listIngredients() {
        List<Ingredient> ingredients = Ingredient.find.all();
        List<IngredientCategory> categories = IngredientCategory.find.all();

        StringBuilder sb = new StringBuilder();
        sb.append("Ingredients:\n");
        for (Ingredient ingredient : ingredients) {
            sb.append("\t[" + ingredient.id + "] " + ingredient.name);
            sb.append(" (Category: [" + ingredient.ingredientCategory.id + "] " + ingredient.ingredientCategory.name + ") ");
            sb.append(" (Aliases: ");
            for (IngredientAlias alias : ingredient.aliases) {
                sb.append("[" + alias.id + "] " + alias.name + ",");
            }
            sb.append(")\n");
        }

        sb.append("\nCategories:\n");
        for (IngredientCategory category : categories) {
            sb.append("\t[" + category.id + "] " + category.name + " (Ingredients: ");
            for (Ingredient ingredient : category.ingredients) {
                sb.append("[" + ingredient.id + "] " + ingredient.name + ",");
            }
            sb.append(")\n");
        }

        return ok(sb.toString());
    }

}