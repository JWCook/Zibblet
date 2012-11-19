package models;

import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Foo extends Model {
    @Id
    public Long id;
    private String name;
    private String category;

    public Foo() {}

    public Foo(String name, String category) {
        this.name = name;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public static Finder<Long, Foo> find = new Finder<Long, Foo>(Long.class, Foo.class);

}