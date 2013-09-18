package models;


import play.data.validation.Constraints.*;
import play.db.ebean.Model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Recipe extends Model {

    @Id
    public Long id;
    @Required
    public String title;
    public String URL;
    public int timesMade;
    public int servings;
    @MaxLength(16)
    public String prepTime;
    @MaxLength(16)
    public String cookTime;
    @MaxLength(64000)
    public String directions;
    @MaxLength(64000)
    public String notes;
    @OneToMany(cascade = CascadeType.ALL)
    public List<ItemQuantity> ingredients = new ArrayList<>();
    @ManyToMany(mappedBy = "recipes")
    public List<RecipeTag> recipeTags = new ArrayList<>();

}
