package models;

import com.avaje.ebean.validation.Length;
import utility.unitconversion.CookingUnit;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 * Represents a quantity of an item, to be used as an entry in a Recipe or ShoppingList
 */
@Entity
public class ItemQuantity {

    @Id
    public Long id;
    public double quantity;
    public String unitStr; // Non-standard unit, e.g., "1 can"
    public String preparation; // Modifier for ingredient, e.g., "chopped," "diced," etc.
    @Length(max=2)
    public CookingUnit unit;
    @OneToOne
    public Ingredient ingredient;

}
