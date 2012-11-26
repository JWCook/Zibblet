package models;

import org.apache.commons.io.FileUtils;
import org.junit.BeforeClass;
import play.test.FakeApplication;

import java.io.IOException;

import static play.test.Helpers.*;

public class BaseModelTest {
    private static final String EVOLUTION_1 = "conf/evolutions/default/1.sql";
    private static final String EVOLUTION_2 = "conf/evolutions/default/2.sql";
    private static FakeApplication app;
    private static String createDdl = "";
    private static String dropDdl = "";
    private static String createData = "";
    private static String dropData = "";

    @BeforeClass
    public static void startApp() throws IOException {
        app = fakeApplication(inMemoryDatabase());
        start(app);

        // Parse evolution files for DDL and data creation/deletion statements
//        String[] evolution1 = splitEvolutionContent(EVOLUTION_1);
//        String[] evolution2 = splitEvolutionContent(EVOLUTION_2);
//        createDdl = evolution1[0];
//        dropDdl = evolution1[1];
//        createData = evolution2[0];
//        dropData = evolution2[1];

//        Ebean.execute(Ebean.createCallableSql(createDdl));
    }
//
//    @Before
//    public void createData() {
//        Ebean.execute(Ebean.createCallableSql(dropData));
//        Ebean.execute(Ebean.createCallableSql(createData));
//    }
//
//    @After
//    public void dropData() {
//        Ebean.execute(Ebean.createCallableSql(dropData));
//    }
//
//    @AfterClass
//    public static void stopApp() {
//        Ebean.execute(Ebean.createCallableSql(dropDdl));
//        Ebean.execute(Ebean.createCallableSql(createDdl));
//        stop(app);
//    }

    /**
     * Parse an evolution file into ups (data/DDL creation) and downs (data/DDL deletion)
     * @param evolutionFilePath File path to the evolution file to parse
     * @return A String array with 2 elements, containing the ups and downs defined in the evolution file
     * @throws IOException If there was an error reading the evolution file
     */
    private static String[] splitEvolutionContent(String evolutionFilePath) throws IOException {
        String evolutionContent = FileUtils.readFileToString(app.getWrappedApplication().getFile(evolutionFilePath));
        String[] splitEvolutionContent = evolutionContent.split("# --- !Ups");
        return splitEvolutionContent[1].split("# --- !Downs");
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
}