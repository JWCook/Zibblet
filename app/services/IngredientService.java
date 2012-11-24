package services;

import models.Ingredient;

public class IngredientService {

    public static Ingredient findIngredient(String name) {
        return Ingredient.find.fetch("ingredientCategory")
                .where().eq("name", name)
                .where().eq("isTempItem", "false").findUnique();
    }

}
