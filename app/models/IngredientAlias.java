package models;

import play.data.validation.Constraints;
import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class IngredientAlias extends Model {

    @Id
    public Long id;
    @Constraints.Required
    public String name;
    @ManyToOne
    public Ingredient ingredient;

}
