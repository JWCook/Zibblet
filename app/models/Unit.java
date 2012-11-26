package models;

import com.avaje.ebean.annotation.EnumValue;
import com.avaje.ebean.validation.Length;
import play.data.validation.Constraints;
import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Represents a unit of measurement. Instead of maintaining a separate conversion matrix, each unit stores a
 * ratio of this unit to base unit for that measurement type. E.g., the unit pounds has a conversion ratio
 * of 453.592 (to grams, the base unit for weight/mass). To convert between any two other units, their respective
 * conversion ratios can simply be divided.
 */
@Entity
public class Unit extends Model {

    public static Finder<Long, Unit> find = new Finder<Long, Unit>(Long.class, Unit.class);

    public enum MeasurementSystem {
        @EnumValue("SI") METRIC,
        @EnumValue("US") US
    }

    public enum MeasurementType {
        @EnumValue("V") VOLUME,     // Base unit: cubic centimeters
        @EnumValue("W") WEIGHT,     // Base unit: grams
    }

    @Id
    public Long id;
    @Constraints.Required
    public String name;
    @Length(max=10)
    public String symbol;
    public double conversionRatio; // Constant used to convert this unit to base unit
    @Length(max=2)
    public MeasurementSystem system;
    @Length(max=1)
    public MeasurementType type;

}
