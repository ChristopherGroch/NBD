package Client;

import jakarta.json.bind.annotation.JsonbCreator;
import jakarta.json.bind.annotation.JsonbProperty;

public class Test {
    @JsonbProperty("a")
    private int a;
    @JsonbProperty("id")
    private int id;

    @JsonbCreator
    public Test(@JsonbProperty("a") int a,@JsonbProperty("id") int id) {
        this.a = a;
        this.id = id;
    }


    public int getA() {
        return a;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Client.Test{" +
                "a=" + a +
                ", id=" + id +
                '}';
    }
}
