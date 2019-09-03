package lambdaStudy.lambdaStudy2;

public class Person {
    private String name;
    private String secondName;
    private int id;

    public Person(String name, String secondName, int id) {
        this.name = name;
        this.secondName = secondName;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", secondName='" + secondName + '\'' +
                ", id=" + id +
                '}';
    }
}
