package models;

import org.junit.Test;
import services.IngredientService;

import static org.fest.assertions.Assertions.assertThat;

public class IngredientTest extends BaseModelTest<Ingredient> {

    @Test
    public void testCreateAndReadIngredient() {
        String categoryName = "category 1";
        String ingredientName = "ingredient 1";
        String aliasName = "ingredient 1a";

        IngredientCategory category = new IngredientCategory();
        category.name = categoryName;
        category.save();

        Ingredient ingredient = new Ingredient();
        ingredient.name = ingredientName;
        ingredient.ingredientCategory = category;
        ingredient.save();

        IngredientAlias alias = new IngredientAlias();
        alias.name = aliasName;
        alias.ingredient = ingredient;
        alias.save();

        Ingredient foundIngredient = IngredientService.findIngredient(ingredientName);
        assertThat(foundIngredient.name).matches(ingredientName);
        assertThat(foundIngredient.ingredientCategory.name).matches(categoryName);
        assertThat(foundIngredient.aliases.size()).isEqualTo(1);
        assertThat(foundIngredient.aliases.get(0).name).matches(aliasName);
        foundIngredient.delete();
        assertThat(Ingredient.find.all().size() == 0);
    }

}