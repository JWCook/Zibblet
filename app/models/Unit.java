package models;

import com.avaje.ebean.annotation.EnumValue;
import com.avaje.ebean.validation.Length;
import play.data.validation.Constraints;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Represents a unit of measurement. Instead of maintaining a separate conversion matrix, each unit stores a
 * ratio of this unit to base unit for that measurement type. E.g., the unit pounds has a conversion ratio
 * of 453.592 (to grams, the base unit for weight/mass). To convert between any two other units, their respective
 * conversion ratios can simply be divided.
 *
 * For now, using base units of cubic centimeters for volume, and grams for mass. These base units are not explicitly
 * persisted anywhere, since an extra join with a 2-element table
 */
@Entity
public class Unit {

    public static enum MeasurementType {
        @EnumValue("V")
        VOLUME, // Base unit: cubic centimeters
        @EnumValue("W")
        WEIGHT // Base unit: grams
    }

    @Id
    public Long id;
    @Constraints.Required
    public String name;
    @Length(max=10)
    public String symbol;
    // Constant used to convert this unit to base unit
    public Double conversionRatio;
    @Length(max=6)
    public String system;
    @Length(max=1)
    public MeasurementType type;

}
