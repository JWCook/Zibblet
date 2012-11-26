package models;

import play.data.validation.Constraints;
import play.db.ebean.Model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Ingredient extends Model {

    public static Finder<Long, Ingredient> find = new Finder<Long, Ingredient>(Long.class, Ingredient.class);

    @Id
    public Long id;
    @Constraints.Required
    public String name;
    public double density;
    public boolean isTempItem;
    @ManyToOne(fetch = FetchType.LAZY)
    public IngredientCategory ingredientCategory;
    @OneToMany(cascade = CascadeType.ALL)
    public List<IngredientAlias> aliases;
    @ManyToMany
    public List<Recipe> recipes;

    public static Ingredient findIngredient(String name) {
        return Ingredient.find.fetch("ingredientCategory")
                .where().eq("name", name)
                .where().eq("isTempItem", "false").findUnique();
    }

    public static List<Ingredient> findTempIngredients() {
        return Ingredient.find.where().eq("isTempItem", "true").findList();
    }
}