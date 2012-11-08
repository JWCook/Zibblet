import models.Foo;
import org.fest.assertions.Assertions;
import org.junit.Test;

import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.running;

public class FooTest {

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