package models;


import com.avaje.ebean.validation.Length;
import play.data.validation.Constraints;
import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Recipe extends Model {

    @Id
    public Long id;
    @Constraints.Required
    public String title;
    public int timesMade;
    public String URL;
    @Length(max=16)
    public String prepTime;
    @Length(max=16)
    public String cookTime;
    @Length(max=64000)
    public String directions;
    @Length(max=64000)
    public String notes;
    @ManyToMany(mappedBy = "recipes")
    public List<Ingredient> ingredients = new ArrayList<>();
    @ManyToMany(mappedBy = "recipes")
    public List<RecipeTag> recipeTags = new ArrayList<>();

}
