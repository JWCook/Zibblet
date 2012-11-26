package utility.unitconversion;

import org.junit.BeforeClass;
import org.junit.Test;

import java.math.RoundingMode;
import java.text.DecimalFormat;

import static org.fest.assertions.Assertions.assertThat;
import static utility.unitconversion.UnitConverter.*;
import static utility.unitconversion.CookingUnit.*;
import static utility.unitconversion.CookingFraction.*;

public class UnitConverterTest {
    private static DecimalFormat twoDForm;

    @BeforeClass
    public static void setUp () {
        twoDForm = new DecimalFormat("#.##");
        twoDForm.setRoundingMode(RoundingMode.HALF_UP);
    }

    @Test
    public void testConvertFluidOuncesToMilliliters() {
        double ounces = 2.0;
        double milliliters = convertUnits(ounces, FLUID_OUNCE, MILLILITER);
        assertThat(Double.valueOf(twoDForm.format(milliliters))).isEqualTo(59.15);
    }

    @Test
    public void testConvertQuartsToLiters() {
        double quarts = 5.0;
        double liters = convertUnits(quarts, QUART, LITER);
        assertThat(Double.valueOf(twoDForm.format(liters))).isEqualTo(4.73);
    }

    @Test
    public void testConvertOuncesToGrams() {
        double quarts = 15.2;
        double liters = convertUnits(quarts, OUNCE, GRAM);
        assertThat(Double.valueOf(twoDForm.format(liters))).isEqualTo(430.91);
    }

    @Test
    public void testConvertToFractionExact() {
        assertThat(convertToFraction(0.0)).isEqualTo("");
        assertThat(convertToFraction(0.125)).isEqualTo("1/8");
        assertThat(convertToFraction(0.25)).isEqualTo("1/4");
        assertThat(convertToFraction(1.0 / 3.0)).isEqualTo("1/3");
        assertThat(convertToFraction(0.375)).isEqualTo("3/8");
        assertThat(convertToFraction(0.5)).isEqualTo("1/2");
        assertThat(convertToFraction(0.625)).isEqualTo("5/8");
        assertThat(convertToFraction(2.0 / 3.0)).isEqualTo("2/3");
        assertThat(convertToFraction(0.75)).isEqualTo("3/4");
        assertThat(convertToFraction(0.875)).isEqualTo("7/8");
        assertThat(convertToFraction(0.999)).isEqualTo("1");
    }

    @Test
    public void testConvertToFractionMinExclusive() {
        assertThat(convertToFraction(ZERO.getMin())).isEqualTo(ZERO.toString());
        assertThat(convertToFraction(ONE_EIGHTH.getMin())).isEqualTo(ZERO.toString());
        assertThat(convertToFraction(ONE_FOURTH.getMin())).isEqualTo(ONE_EIGHTH.toString());
        assertThat(convertToFraction(ONE_THIRD.getMin())).isEqualTo(ONE_FOURTH.toString());
        assertThat(convertToFraction(THREE_EIGHTHS.getMin())).isEqualTo(ONE_THIRD.toString());
        assertThat(convertToFraction(ONE_HALF.getMin())).isEqualTo(THREE_EIGHTHS.toString());
        assertThat(convertToFraction(FIVE_EIGHTHS.getMin())).isEqualTo(ONE_HALF.toString());
        assertThat(convertToFraction(TWO_THIRDS.getMin())).isEqualTo(FIVE_EIGHTHS.toString());
        assertThat(convertToFraction(THREE_FOURTHS.getMin())).isEqualTo(TWO_THIRDS.toString());
        assertThat(convertToFraction(SEVEN_EIGHTHS.getMin())).isEqualTo(THREE_FOURTHS.toString());
        assertThat(convertToFraction(ONE.getMin())).isEqualTo(SEVEN_EIGHTHS.toString());
    }

    @Test
    public void testConvertToFractionMaxInclusive() {
        assertThat(convertToFraction(ZERO.getMax())).isEqualTo(ZERO.toString());
        assertThat(convertToFraction(ONE_EIGHTH.getMax())).isEqualTo(ONE_EIGHTH.toString());
        assertThat(convertToFraction(ONE_FOURTH.getMax())).isEqualTo(ONE_FOURTH.toString());
        assertThat(convertToFraction(ONE_THIRD.getMax())).isEqualTo(ONE_THIRD.toString());
        assertThat(convertToFraction(THREE_EIGHTHS.getMax())).isEqualTo(THREE_EIGHTHS.toString());
        assertThat(convertToFraction(ONE_HALF.getMax())).isEqualTo(ONE_HALF.toString());
        assertThat(convertToFraction(FIVE_EIGHTHS.getMax())).isEqualTo(FIVE_EIGHTHS.toString());
        assertThat(convertToFraction(TWO_THIRDS.getMax())).isEqualTo(TWO_THIRDS.toString());
        assertThat(convertToFraction(THREE_FOURTHS.getMax())).isEqualTo(THREE_FOURTHS.toString());
        assertThat(convertToFraction(SEVEN_EIGHTHS.getMax())).isEqualTo(SEVEN_EIGHTHS.toString());
        assertThat(convertToFraction(ONE.getMax() - 0.0001)).isEqualTo(ONE.toString());
    }
}
