package utility.unitconversion;

import com.avaje.ebean.annotation.EnumValue;

import static utility.unitconversion.CookingUnit.MeasurementSystem.*;
import static utility.unitconversion.CookingUnit.MeasurementType.*;

public enum CookingUnit {
    @EnumValue("g")     GRAM       ("g",       1.0,        SI, WEIGHT),
    @EnumValue("mg")    MILLIGRAM  ("mg",      0.001,      SI, WEIGHT),
    @EnumValue("kg")    KILOGRAM   ("kg",      1000.0,     SI, WEIGHT),
    @EnumValue("ml")    MILLILITER ("ml",      1.0,        SI, VOLUME),
    @EnumValue("l")     LITER      ("l",       1000.0,     SI, VOLUME),
    @EnumValue("oz")    OUNCE      ("oz",      28.3495,    US, WEIGHT),
    @EnumValue("lb")    POUND      ("lb",      453.5924,   US, WEIGHT),
    @EnumValue("tsp")   TEASPOON   ("tsp",     4.9289,     US, VOLUME),
    @EnumValue("tbsp")  TABLESPOON ("tbsp",    14.7868,    US, VOLUME),
    @EnumValue("fl oz") FLUID_OUNCE("fl oz",   29.5735,    US, VOLUME),
    @EnumValue("c")     CUP        ("c",       236.5882,   US, VOLUME),
    @EnumValue("pt")    PINT       ("pt",      473.1765,   US, VOLUME),
    @EnumValue("qt")    QUART      ("qt",      946.3529,   US, VOLUME),
    @EnumValue("gal")   GALLON     ("gal",     3785.4118,  US, VOLUME);

    public enum MeasurementSystem {
        SI,
        US
    }

    public enum MeasurementType {
        VOLUME,     // Base unit: milliliters
        WEIGHT,     // Base unit: grams
    }

    private String symbol;
    private double conversionRatio;
    private MeasurementSystem system;
    private MeasurementType type;

    private CookingUnit(String symbol, double conversionRatio, MeasurementSystem system, MeasurementType type) {
        this.symbol = symbol;
        this.conversionRatio = conversionRatio;
        this.system = system;
        this.type = type;
    }
    public String getName() {
        return name().toLowerCase().replace('_', ' ');
    }
    public String getPluralName() {
        return getName() + "s";
    }
    public String getSymbol() {
        return symbol;
    }
    public double getConversionRatio() {
        return conversionRatio;
    }
    public MeasurementSystem getSystem() {
        return system;
    }
    public MeasurementType getType() {
        return type;
    }
}