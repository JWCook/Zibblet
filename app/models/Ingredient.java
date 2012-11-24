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
    public Double density;
    public boolean isTempItem;
    @ManyToOne(fetch = FetchType.LAZY)
    public IngredientCategory ingredientCategory;
    @OneToMany(cascade = CascadeType.ALL)
    public List<IngredientAlias> aliases;
    @ManyToMany
    public List<Recipe> recipes;

}