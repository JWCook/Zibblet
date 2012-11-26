package models;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import play.test.FakeApplication;

import java.io.IOException;

import static play.test.Helpers.*;

public class BaseModelTest {
    private static FakeApplication app;

    @BeforeClass
    public static void startApp() throws IOException {
        app = fakeApplication(inMemoryDatabase());
        start(app);
    }

    @AfterClass
    public static void stopApp() {
        stop(app);
    }

}