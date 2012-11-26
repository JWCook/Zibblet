package utility.unitconversion;

import models.BaseModelTest;
import models.Unit;
import org.junit.Test;

import java.io.UnsupportedEncodingException;

public class UnitConverterTest extends BaseModelTest {

    @Test
    public void testConvertUnits() throws UnsupportedEncodingException{
        for (Unit unit : Unit.find.all()) {
            System.out.println("Unit [" + unit.id + "]: " + unit.name + " (" + unit.symbol + ") " + unit.system + " " + unit.type);
        }

    }
}
