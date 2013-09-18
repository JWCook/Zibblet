package models;

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

    // TODO: join with aliases (separate query?)
    public static Ingredient findIngredient(String name) {
        return find.fetch("category")
                .where().eq("name", name)
                .where().eq("isTempItem", "false")
                .where().eq("isSubtype", "false").findUnique();
    }

    public static List<Ingredient> findIngredients() {
        return find.fetch("category")
                .where().eq("isTempItem", "false")
                .where().eq("isSubtype", "false").findList();
    }

    public static List<Ingredient> findTempIngredients() {
        return find.where().eq("isTempItem", "true").findList();
    }

    public static Ingredient findSubtype(String name) {
        return find.fetch("supertype")
                .where().eq("name", name)
                .where().eq("isSubtype", "true").findUnique();
    }

    public void addSubtype(Ingredient subType) {
        if (isSubtype) {
            throw new UnsupportedOperationException("Cannot add a subtype to a subtype");
        }
        subType.isSubtype = true;
        subType.supertype = this;
        subType.save();
    }

    public void saveAsTempIngredient() {
        isTempItem = true;
        save();
    }

    /**
     * Workaround for broken cascade deletion for One-To-Many self-relationships in EBean ORM.
     * TODO: Replace with cascade delete once EBean bug #420 is fixed
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