package models;

import org.junit.Test;
import static models.Unit.MeasurementSystem.*;
import static  models.Unit.MeasurementType.*;
import static org.fest.assertions.Assertions.assertThat;

public class MeasurementUnitTest extends BaseModelTest {

    @Test
    public void testCreateUnit() {
        String name = "test unit";
        String symbol = "tu";
        Double conversionRatio = 2.0;

        Unit unit = new Unit();
        unit.name = name;
        unit.symbol = symbol;
        unit.conversionRatio = conversionRatio;
        unit.system = METRIC;
        unit.type = WEIGHT;
        unit.save();

        Unit foundUnit = Unit.find.where().eq("name", name).findUnique();
        assertThat(unit.name).matches(name);
        assertThat(unit.symbol).matches(symbol);
        assertThat(unit.conversionRatio).isEqualTo(conversionRatio);
        assertThat(unit.system == METRIC);
        assertThat(unit.type == WEIGHT);
        foundUnit.delete();
        assertThat(Unit.find.where().eq("name", name).findUnique()).isNull();
    }

}
