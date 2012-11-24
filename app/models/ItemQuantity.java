package models;

import play.data.validation.Constraints;

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
    @Constraints.Required
    public Integer quantity;
    @OneToOne
    public Unit unit;
    @OneToOne
    public Ingredient ingredient;

}
