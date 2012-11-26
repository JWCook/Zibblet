package utility.unitconversion;

public class UnitConverter {

    private UnitConverter() {}

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

        return (whole > 0) ? (whole + fractionStr) : fractionStr;
    }

    /**
     * Convert a value from one unit to another
     * @param value The value to convert
     * @param fromUnit The unit of the original value
     * @param toUnit The unit to convert the value to
     * @return The value converted to the unit specified by toUnit
     */
    public static Double convertUnits(Double value, CookingUnit fromUnit, CookingUnit toUnit) {
        return value * (fromUnit.getConversionRatio() / toUnit.getConversionRatio());
    }

}
