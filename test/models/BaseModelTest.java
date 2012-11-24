package models;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import play.db.ebean.Model;
import play.test.FakeApplication;

import static play.test.Helpers.*;

public class BaseModelTest<T extends Model> {

    public static FakeApplication app;

    @BeforeClass
    public static void startApp() {
        app = fakeApplication(inMemoryDatabase());
        start(app);
    }
//
//    @Before
//    public void beforeEachTest() {
//        save(fixturesToLoad());
//    }
//
//    @After
//    public void afterEachTest() {
//        delete(fixturesToUnload());
//    }
//
//    public abstract List<T> fixturesToLoad();
//
//    public abstract List<T> fixturesToUnload();

    @AfterClass
    public static void stopApp() {
        stop(app);
    }

}