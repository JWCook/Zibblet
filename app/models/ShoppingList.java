package models;

import play.data.format.Formats;
import play.data.validation.Constraints;
import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class ShoppingList extends Model {

    @Id
    public Long id;
    @Constraints.Required
    public String title;
    @Formats.DateTime(pattern="yyyy-MM-dd")
    public Date createdDate;
    public List<Ingredient> ingredients = new ArrayList<>();

}
