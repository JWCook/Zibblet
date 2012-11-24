package models;


import play.data.validation.Constraints;
import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.List;

@Entity
public class RecipeTag extends Model {

    @Id
    public Long id;
    @Constraints.Required
    public String name;
    @ManyToMany
    List<Recipe> recipes;

}
