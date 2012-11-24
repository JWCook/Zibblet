package models;

import play.data.validation.Constraints;
import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class IngredientCategory extends Model {

    public static Finder<Long, IngredientCategory> find = new Finder<Long, IngredientCategory>(Long.class, IngredientCategory.class);

    @Id
    public Long id;
    @Constraints.Required
    public String name;
    @OneToMany
    public List<Ingredient> ingredients = new ArrayList<>();

}
