package lambdaStudy.lambdaStudy2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class main {
    public static void main(String[] args) {
        List<Person> personList = new ArrayList<>();
        personList.add(new Person("A", "w", 12));
        personList.add(new Person("D", "q", 34));
        personList.add(new Person("J", "e", 15));
        personList.add(new Person("B", "r", 7));
        personList.add(new Person("Q", "s", 1));

//        Collections.sort(personList, (p1, p2) -> p1.getName().compareTo(p2.getName()));
//        // одно и тоже
//        Collections.sort(personList, new Comparator<Person>() {
//            @Override
//            public int compare(Person o1, Person o2) {
//                return o1.getName().compareTo(o2.getName());
//            }
//        });
        // for (Person p: personList) { System.out.println(p);}
        //тоже самое
        // personList.stream().forEach(p-> System.out.println(p));
        // personList.stream().forEach(System.out::println);

        //вывод по фльтру на лету
        personList.stream()
                .sorted((p1, p2) -> p1.getName().compareTo(p2.getName()))
                .filter(p -> p.getId() > 2)
                .forEach(System.out::println);
        personList.stream()
                .sorted((p1, p2) -> p1.getName().compareTo(p2.getName()))
                .filter(p -> p.getId() > 2)
                .map(p->p.getName())
                .forEach(System.out::println);
    }
}
