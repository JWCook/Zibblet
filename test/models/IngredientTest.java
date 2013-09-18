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
        for (Ingredient ingredient : Ingredient.find.where().eq("isSubtype", "false").findList()) {
            ingredient.deleteIngredientAndSubtypes();
        }
        for (IngredientAlias alias : aliasFinder.all()) {
            alias.delete();
        }
        for (IngredientCategory category : IngredientCategory.find.all()) {
            category.deleteCategory();
        }
    }

    /*
     * General
     */

    @Test
    public void testCreateIngredient() {
        Ingredient ingredient = Ingredient.find.where().eq("name", "ingredient 1").findUnique();
        assertThat(ingredient.name).isEqualTo("ingredient 1");
    }

    @Test
    public void testDeleteIngredient() {
        Ingredient ingredient = Ingredient.find.where().eq("name", "ingredient 1").findUnique();
        assertThat(ingredient).isNotNull();
        ingredient.deleteIngredientAndSubtypes();
        ingredient = ingredient.find.where().eq("name", "ingredient 1").findUnique();
        assertThat(ingredient).isNull();
    }

    @Test
    public void testFindIngredients() {
        List<Ingredient> ingredients = Ingredient.findIngredients();
        assertThat(ingredients).hasSize(1);
    }

    /*
     * Aliases
     */

    @Test
    public void testAddAlias() {
        Ingredient ingredient = Ingredient.find.where().eq("name", "ingredient 1").findUnique();
        assertThat(ingredient.aliases.size()).isEqualTo(1);
        assertThat(ingredient.aliases.get(0).name).matches("ingredient 1a");
    }

    @Test
    public void testDeleteIngredientDeletesAlias() {
        Ingredient.find.where().eq("name", "ingredient 1").findUnique().deleteIngredientAndSubtypes();
        IngredientAlias alias = aliasFinder.where().eq("name", "ingredient 1a").findUnique();
        assertThat(alias).isNull();
    }

    @Test
    public void testDeleteAliasDoesNotDeleteIngredient() {
        aliasFinder.where().eq("name", "ingredient 1a").findUnique().delete();
        Ingredient ingredient = Ingredient.find.where().eq("name", "ingredient 1").findUnique();
        assertThat(ingredient).isNotNull();
    }

    /*
     * Subtypes
     */

    @Test
    public void testAddSubtype() {
        Ingredient ingredient = Ingredient.find.where().eq("name", "ingredient 1").findUnique();
        List<Ingredient> subtypes = ingredient.subtypes;
        assertThat(subtypes).isNotEmpty();
        assertThat(subtypes.get(0).name).isEqualTo("ingredient 1, modified");
    }

    @Test
    public void testGetSupertypeFromSubtypeLookup() {
        Ingredient subtype = Ingredient.find.fetch("supertype").where().eq("name", "ingredient 1, modified").findUnique();
        assertThat(subtype).isNotNull();

        Ingredient supertype = subtype.supertype;
        assertThat(supertype).isNotNull();
        assertThat(supertype.name).isEqualTo("ingredient 1");
    }

    @Test
    public void testGetSupertypeFromSubtypeMember() {
        Ingredient ingredient = Ingredient.find.where().eq("name", "ingredient 1").findUnique();
        List<Ingredient> subtypes = ingredient.subtypes;
        Ingredient subtypeSupertype = subtypes.get(0).supertype;
        assertThat(subtypeSupertype).isNotNull();
    }

    @Test(expected=UnsupportedOperationException.class)
    public void testAddSubtypeToSubtypeFails() {
        Ingredient subtype = Ingredient.find.where().eq("name", "ingredient 1, modified").findUnique();
        Ingredient subsubtype = new Ingredient();
        subsubtype.name = "subtype of ingredient 1, modified";
        subtype.addSubtype(subsubtype);
    }

    @Test
    public void testAddSubtypeToSubtypeDoesNotPersist() {
        Ingredient subtype = Ingredient.find.where().eq("name", "ingredient 1, modified").findUnique();
        Ingredient subsubtype = new Ingredient();
        subsubtype.name = "subtype of ingredient 1, modified";
        try {
            subtype.addSubtype(subsubtype);
        } catch (UnsupportedOperationException e) {}

        List<Ingredient> subsubtypes = subtype.subtypes;
        assertThat(subsubtypes).isEmpty();
        subsubtype = Ingredient.find.where().eq("name", "subtype of ingredient 1, modified").findUnique();
        assertThat(subsubtype).isNull();
    }

    @Test
    public void testDeleteIngredientDeletesSubtypes() {
        Ingredient.find.where().eq("name", "ingredient 1").findUnique().deleteIngredientAndSubtypes();
        Ingredient subtype = Ingredient.find.where().eq("name", "ingredient 1, modified").findUnique();
        assertThat(subtype).isNull();
    }


    @Test
    public void testDeleteSubtypeDoesNotDeleteIngredient() {
        Ingredient.find.where().eq("name", "ingredient 1, modified").findUnique().deleteIngredientAndSubtypes();
        Ingredient ingredient = Ingredient.find.where().eq("name", "ingredient 1").findUnique();
        assertThat(ingredient).isNotNull();
    }

    /*
     * Categories
     */

    @Test
    public void testAddCategory() {
        Ingredient ingredient = Ingredient.find.fetch("category").where().eq("name", "ingredient 1").findUnique();
        assertThat(ingredient.category.name).matches("category 1");
    }

    @Test
    public void testDeleteIngredientDoesNotDeleteCategory() {
        Ingredient.find.where().eq("name", "ingredient 1").findUnique().deleteIngredientAndSubtypes();
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

    /*
     * Temp Ingredients
     */

    @Test
    public void testCreateTempIngredient() {
        Ingredient tempIngredient = new Ingredient();
        tempIngredient.name = "temp ingredient 1";
        tempIngredient.saveAsTempIngredient();

        Ingredient ingredient = Ingredient.find.where().eq("name", "temp ingredient 1").findUnique();
        assertThat(ingredient.name).isEqualTo("temp ingredient 1");
    }

    @Test
    public void testDeleteTempIngredient() {
        Ingredient tempIngredient = new Ingredient();
        tempIngredient.name = "temp ingredient 1";
        tempIngredient.saveAsTempIngredient();

        Ingredient ingredient = Ingredient.find.where().eq("name", "temp ingredient 1").findUnique();
        assertThat(ingredient).isNotNull();
        ingredient.deleteIngredientAndSubtypes();
        ingredient = ingredient.find.where().eq("name", "temp ingredient 1").findUnique();
        assertThat(ingredient).isNull();
    }

    @Test
    public void testListIngredientsExcludesTempIngredients() {
        Ingredient tempIngredient = new Ingredient();
        tempIngredient.name = "temp ingredient 1";
        tempIngredient.saveAsTempIngredient();

        List<Ingredient> ingredients = Ingredient.findIngredients();
        assertThat(ingredients).hasSize(1);
    }

}