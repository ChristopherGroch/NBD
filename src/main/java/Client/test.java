package Client;

import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonRepresentation;

import java.io.Serializable;


public class test implements Serializable {

    @BsonProperty("_id")
    private String name;
    @BsonProperty("age")
    private Integer age;

    public String getName() {
        return name;
    }
    public test(){}
    public void setName(String name) {
        this.name = name;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getAge() {
        return age;
    }

    @BsonCreator
    public test(@BsonProperty("_id") String name,
                      @BsonProperty("age") Integer age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "test{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}