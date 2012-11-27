package utility.unitconversion;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class UnitConverter {

    private static final int DISPLAY_PRECISION = 2;

    private UnitConverter() {}

    public static double convertCelsiusToFahrenheit(double celsius) {
        return (celsius * 1.8) + 32;
    }

    public static double convertFahrenheitToCelsius(double fahrenheit) {
        return (fahrenheit - 32) / 1.8;
    }

    /**
     * Convert a value from one unit to another
     * @param value The value to convert
     * @param fromUnit The unit of the original value
     * @param toUnit The unit to convert the value to
     * @return The value converted to the unit specified by toUnit
     */
    public static double convertUnits(double value, CookingUnit fromUnit, CookingUnit toUnit) {
        return value * (fromUnit.getConversionRatio() / toUnit.getConversionRatio());
    }

    /**
     * Convert a double value to a fraction, reduced to the nearest 1/2, 1/3, 1/4, or 1/8, used to represent
     * amounts of ingedients in US units
     * @param value A double value to convert
     * @return The specified double in fraction form, reduced to the nearest 1/2, 1/3, 1/4, or 1/8
     */
    public static String convertToFraction(double value) {
        int whole = (int) value;
        double decimal = value - whole;
        String fractionStr = "";


        // Iterate over fractions and check the original value's decimal part against each fraction's decimal range
        for (CookingFraction fraction : CookingFraction.values()) {
            if (decimal > fraction.getMin() && decimal <= fraction.getMax()) {
                fractionStr = fraction.toString();
                break;
            }
        }

        if (fractionStr.equals(CookingFraction.ONE.toString())) {
            whole++;
            fractionStr = "";
        }

        String formattedFraction;
        if (whole > 0 && fractionStr.length() > 0) {
            formattedFraction = whole + " " + fractionStr;
        }
        else if (whole > 0) {
            formattedFraction = "" + whole;
        }
        else if (fractionStr.length() > 0) {
            formattedFraction = fractionStr;
        }
        else {
            formattedFraction = "0";
        }

        return formattedFraction;
    }

    public static double round(double value) {
        BigDecimal rounded = new BigDecimal(value).setScale(DISPLAY_PRECISION, RoundingMode.HALF_UP);
        return rounded.doubleValue();
    }

}
