import models.Foo;
import org.junit.Test;
import play.mvc.Result;

import static org.fest.assertions.Assertions.assertThat;
import static play.test.Helpers.*;
public class FooTest {

    @Test
    public void testAddFoo() {
        running(fakeApplication(), new Runnable() {
            public void run() {
                Result addFooResult = routeAndCall(fakeRequest(POST, "/addFoo/FOOBAR"));
                Result listFoosResult = routeAndCall(fakeRequest(GET, "/listFoos"));
                assertThat(listFoosResult).isNotNull();
            }
        });
    }

    @Test
    public void integrationTest() {
        running(fakeApplication(), new Runnable() {
            public void run() {
                Foo foo = new Foo("asdf", "jkl");
                foo.save();
                assertThat(Foo.find.all().size() >= 1);
                foo.delete();
                assertThat(Foo.find.all().size() == 0);
            }
        });

    }

}