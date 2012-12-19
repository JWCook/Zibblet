package models;

import com.avaje.ebean.annotation.Transactional;
import play.data.validation.Constraints;
import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class IngredientCategory extends Model {
    public static final String UNCATEGORIZED = "Uncategorized";

    public static Finder<Long, IngredientCategory> find = new Finder(Long.class, IngredientCategory.class);

    @Id
    public Long id;
    @Constraints.Required
    public String name;
    @OneToMany (mappedBy = "category")
    public List<Ingredient> ingredients = new ArrayList<>();

    /**
     * TODO: Safely delete this category, moving any ingredients belonging to this category to "Uncategorized"
     */
    @Transactional
    public void deleteCategory() {
        IngredientCategory uncategorized = find.where().eq("name", UNCATEGORIZED).findUnique();
        for (Ingredient ingredient : ingredients) {
            ingredient.category = uncategorized;
            ingredient.save();
        }
        this.save();
        this.delete();
    }

}
