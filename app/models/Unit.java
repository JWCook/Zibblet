package models;

import com.avaje.ebean.validation.Length;
import play.data.validation.Constraints;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Unit {

    @Id
    public Long id;
    @Constraints.Required
    public String name;
    @Length(max=10)
    public String symbol;
    public Double conversionRatio;
    @Length(max=6)
    public String system;
    public char type;

}
