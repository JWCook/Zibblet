package controllers;

import models.Foo;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;
import static play.libs.Json.toJson;

public class Application extends Controller {
  
  public static Result index() {
    return ok(index.render("hello, world"));
  }

    public static Result addFoos() {
        new Foo("name1", "cat1").save();
        new Foo("name2", "cat2").save();
        new Foo("name3", "cat3").save();
        new Foo("name4", "cat4").save();
        new Foo("name5", "cat5").save();

        return ok(index.render("Added new Foos"));
    }

    public static Result addFoo(String name) {
        Foo foo = new Foo(); foo.setName(name); foo.setCategory("CATEGORY_DEFAULT");
        foo.save();
        return ok(index.render("Added new Foo [" + foo.getName() + " : " + foo.getCategory() + "]"));
    }

    public static Result listFoos() {
        return ok(toJson(Foo.find.all()));
    }

    private static void addFoo2(Foo foo) {
        foo.save();
    }
  
}