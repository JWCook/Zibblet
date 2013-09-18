package models;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.annotation.Transactional;
import play.data.validation.Constraints;
import play.db.ebean.Model;

import javax.persistence.*;
import java.util.List;

/**
 * Representation of an Ingredient.
 * An ingredient may have zero or more subtypes, also stored as an ingredient.
 * An ingredient belongs to a category (default: "Uncategorized").
 */
@Entity
public class Ingredient extends Model {

    public static Finder<Long, Ingredient> find = new Finder(Long.class, Ingredient.class);

    @Id
    public Long id;
    @Constraints.Required
    public String name;
    public double density;
    public boolean isSubtype;
    public boolean isTempItem;
    public boolean isUsdaData;
    @ManyToOne
    public Ingredient supertype;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "supertype")
    public List<Ingredient> subtypes;
    @ManyToOne(fetch = FetchType.LAZY)
    public IngredientCategory category;
    @OneToMany(cascade = CascadeType.ALL)
    public List<IngredientAlias> aliases;

    /**
     * Find the ingredient with the specified name
     * @param name The name of the ingredient to find
     * @return A (non-subtype, non-temporary) Ingredient with the specified name, if available
     * TODO: join with aliases (separate query?)
     */
    public static Ingredient findIngredient(String name) {
        return find.fetch("category")
                .where().eq("name", name)
                .where().eq("isTempItem", "false")
                .where().eq("isSubtype", "false").findUnique();
    }

    /**
     * Find all ingredients
     * @return All (non-subtype, non-temporary) ingredients
     */
    public static List<Ingredient> findIngredients() {
        return find.fetch("category")
                .where().eq("isTempItem", "false")
                .where().eq("isSubtype", "false").findList();
    }

    /**
     * Find all temporary ingredients
     * @return All temporary ingredients
     */
    public static List<Ingredient> findTempIngredients() {
        return find.where().eq("isTempItem", "true").findList();
    }

    /**
     * Find a subtype ingredient with the specified name
     * @param name The name of the subtype ingredient to find
     * @return A subtype Ingredient with the specified name, if available
     */
    public static Ingredient findSubtype(String name) {
        return find.fetch("supertype")
                .where().eq("name", name)
                .where().eq("isSubtype", "true").findUnique();
    }

    /**
     * Add a subtype to this Ingredient
     * @param subtype An Ingredient to be created as a subtype of this Ingredient
     */
    public void addSubtype(Ingredient subtype) {
        if (isSubtype) {
            throw new UnsupportedOperationException("Cannot add a subtype to a subtype");
        }
        subtype.isSubtype = true;
        subtype.supertype = this;
        subtype.save();
    }

    /**
     * Save this Ingredient as a new temporary ingredient
     */
    public void saveAsTempIngredient() {
        isTempItem = true;
        save();
    }

    /**
     * Delete all temporary ingredients
     */
    public void deleteTempIngredients() {
        //TODO: replace with Ebean.delete(Collection) once Play includes bugfix for EBean bug #420
        for (Ingredient tempIngredient : find.where().eq("isTempItem", true).findList()) {
            tempIngredient.delete();
        };
    }

    /**
     * Workaround for broken cascade deletion for One-To-Many self-relationships in EBean ORM.
     * TODO: Remove this and replace with normal cascading delete() once Play includes bugfix for EBean bug #420
     */
    @Transactional
    public void deleteIngredientAndSubtypes() {
        if (!isSubtype) {
            for (Ingredient subtype : subtypes) {
                subtype.delete();
            }
        }
        find.where().eq("id", id).findUnique().delete();
    }

}