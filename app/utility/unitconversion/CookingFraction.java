package utility.unitconversion;

/**
 * Definition of common fractions used in cooking, for the purposes of coverting doubles to fractions.
 * This is an ugly implementation, but fast. Since we only have 9 possible fractions (plus 0 and 1), simply checking
 * for fraction ranges requires less math than converting to a rational number and reducing to a denominator of
 * 0/1/2/3/4/8
 */
public enum CookingFraction {
    ZERO            ("0",    0.0000, 0.0625),
    ONE_EIGHTH      ("1/8", 0.0625, 0.1875),
    ONE_FOURTH      ("1/4", 0.1875, 0.2917),
    ONE_THIRD       ("1/3", 0.2917, 0.3542),
    THREE_EIGHTHS   ("3/8", 0.3542, 0.4375),
    ONE_HALF        ("1/2", 0.4375, 0.5625),
    FIVE_EIGHTHS    ("5/8", 0.5625, 0.6458),
    TWO_THIRDS      ("2/3", 0.6458, 0.7083),
    THREE_FOURTHS   ("3/4", 0.7083, 0.8125),
    SEVEN_EIGHTHS   ("7/8", 0.8125, 0.9375),
    ONE             ("1",   0.9375, 1.0000);

    private String str; // String representation of the fraction
    private double min; // Lower end of range (exclusive)
    private double max; // Upper end of range (inclusive)

    CookingFraction(String str, double min, double max) {
        this.str = str;
        this.min = min;
        this.max = max;
    }
    public String toString() {
        return str;
    }
    public double getMin() {
        return min;
    }
    public double getMax(){
        return max;
    }
}
