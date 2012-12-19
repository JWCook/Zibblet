package models;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import play.db.ebean.Model;

import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

public class IngredientTest extends BaseModelTest {
    Model.Finder<Long, IngredientAlias> aliasFinder = new Model.Finder(Long.class, IngredientAlias.class);

    @Before
    public void setUp() {
        IngredientCategory category = new IngredientCategory();
        category.name = "category 1";
        category.save();

        IngredientCategory uncategorized = new IngredientCategory();
        uncategorized.name = IngredientCategory.UNCATEGORIZED;
        uncategorized.save();

        Ingredient ingredient = new Ingredient();
        ingredient.name = "ingredient 1";
        ingredient.category = category;
        ingredient.save();

        IngredientAlias alias = new IngredientAlias();
        alias.name = "ingredient 1a";
        alias.ingredient = ingredient;
        alias.save();

        Ingredient subtype = new Ingredient();
        subtype.name = "ingredient 1, modified";
        ingredient.addSubtype(subtype);
    }

    @After
    public void unload() {
        for (Ingredient ingredient : Ingredient.findIngredients()) {
            ingredient.deleteIngredient();
        }
        for (IngredientAlias alias : aliasFinder.all()) {
            alias.delete();
        }
        for (IngredientCategory category : IngredientCategory.find.all()) {
            category.deleteCategory();
        }
    }

    @Test
    public void testCreateIngredient() {
        Ingredient ingredient = Ingredient.find.where().eq("name", "ingredient 1").findUnique();
        assertThat(ingredient.name).isEqualTo("ingredient 1");
    }

    @Test
    public void testAddAlias() {
        Ingredient ingredient = Ingredient.find.where().eq("name", "ingredient 1").findUnique();
        assertThat(ingredient.aliases.size()).isEqualTo(1);
        assertThat(ingredient.aliases.get(0).name).matches("ingredient 1a");
    }

    @Test
    public void testAddCategory() {
        Ingredient ingredient = Ingredient.find.fetch("category").where().eq("name", "ingredient 1").findUnique();
        assertThat(ingredient.category.name).matches("category 1");
    }

    @Test
    public void testAddSubType() {
        Ingredient ingredient = Ingredient.find.where().eq("name", "ingredient 1").findUnique();
        List<Ingredient> subtypes = ingredient.subtypes;
        assertThat(subtypes).isNotEmpty();
        assertThat(subtypes.get(0).name).isEqualTo("ingredient 1, modified");
    }

    @Test
    public void testDeleteIngredient() {
        Ingredient ingredient = Ingredient.find.where().eq("name", "ingredient 1").findUnique();
        assertThat(ingredient).isNotNull();
        ingredient.deleteIngredient();
        ingredient = ingredient.find.where().eq("name", "ingredient 1").findUnique();
        assertThat(ingredient).isNull();
    }

    @Test
    public void testDeleteIngredientDeletesAlias() {
        Ingredient.find.where().eq("name", "ingredient 1").findUnique().deleteIngredient();
        IngredientAlias alias = aliasFinder.where().eq("name", "ingredient 1a").findUnique();
        assertThat(alias).isNull();
    }

    @Test
    public void testDeleteAliasDoesNotDeleteIngredient() {
        aliasFinder.where().eq("name", "ingredient 1a").findUnique().delete();
        Ingredient ingredient = Ingredient.find.where().eq("name", "ingredient 1").findUnique();
        assertThat(ingredient).isNotNull();
    }

    @Test
    public void testDeleteIngredientDoesNotDeleteCategory() {
        Ingredient.find.where().eq("name", "ingredient 1").findUnique().deleteIngredient();
        IngredientCategory category = IngredientCategory.find.where().eq("name", "category 1").findUnique();
        assertThat(category).isNotNull();
    }

    @Test
    public void testDeleteCategoryDoesNotDeleteIngredient() {
        IngredientCategory.find.where().eq("name", "category 1").findUnique().deleteCategory();
        Ingredient ingredient = Ingredient.find.where().eq("name", "ingredient 1").findUnique();
        assertThat(ingredient).isNotNull();
    }

    @Test
    public void testDeleteCategoryMovesIngredientToUncategorized() {
        IngredientCategory.find.where().eq("name", "category 1").findUnique().deleteCategory();
        Ingredient ingredient = Ingredient.find.fetch("category").where().eq("name", "ingredient 1").findUnique();
        assertThat(ingredient.category.name).isEqualTo(IngredientCategory.UNCATEGORIZED);
    }

    @Test
    public void testDeleteIngredientDeletesSubtypes() {
        Ingredient.find.where().eq("name", "ingredient 1").findUnique().deleteIngredient();
        Ingredient subtype = Ingredient.find.where().eq("name", "ingredient 1, modified").findUnique();
        assertThat(subtype).isNull();
    }

    @Test
    public void testDeleteSubtypeDoesNotDeleteIngredient() {
        Ingredient.find.where().eq("name", "ingredient 1, modified").findUnique().deleteIngredient();
        Ingredient ingredient = Ingredient.find.where().eq("name", "ingredient 1").findUnique();
        assertThat(ingredient).isNotNull();
    }


    // TODO: test temp items

    // TODO: test categories

}