import models.Foo;
import org.fest.assertions.Assertions;
import org.junit.Test;
import play.mvc.Result;

import static org.fest.assertions.Assertions.*;
import static play.test.Helpers.*;

public class FooTest {

    @Test
    public void testFooRoute() {
        Result result = routeAndCall(fakeRequest(GET, "/foo"));
        assertThat(result).isNotNull();
    }

    @Test
    public void integrationTest() {
        running(fakeApplication(), new Runnable() {
            public void run() {
                Foo foo = new Foo();
                foo.setName("asdf");
                foo.setCategory("jkl;");
                foo.save();
                Assertions.assertThat(Foo.find.all().size() >= 1);
                foo.delete();
                Assertions.assertThat(Foo.find.all().size() == 0);
            }
        });

    }

}