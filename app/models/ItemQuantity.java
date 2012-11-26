package models;

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
    @OneToOne
    public Unit unit;
    @OneToOne
    public Ingredient ingredient;

}
