package com.example.sprigboot;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DataCointainer implements ApplicationRunner {

    private final MappingClass repository;

    public DataCointainer(MappingClass repository) {
        this.repository = repository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        initialize();
        System.out.println("Initialized data!");
    }

    public void initialize() {
        ClassroomClass class1 = new ClassroomClass("Metalowcy", 5);
        ClassroomClass class2 = new ClassroomClass("Informatycy", 10);
        ClassroomClass class3 = new ClassroomClass("Głąby", 5);

        StudentClass student1 = new  StudentClass("Bartosz", "Lidwin",  2000, 12.1);
        StudentClass student2 = new  StudentClass("Tomasz", "Ligęza",  2000, 12.1);
        StudentClass student3 = new  StudentClass("Wojciech", "Lugowski",  2000, 28.1);
        StudentClass student4 = new  StudentClass("Stanisław", "Marek",  2000, 31.1);
        StudentClass student5 = new  StudentClass("Jakub", "Litewka",  1999, 11.1);

        class1.addStudent(student1);
        class1.addStudent(student2);
        class1.addStudent(student3);
        class1.addStudent(student4);
        class1.addStudent(student5);

        class2.addStudent(student2);
        class2.addStudent(student3);
        class2.addStudent(student4);

        class3.addStudent(student5);

        repository.classes().put(1L, class1);
        repository.classes().put(2L, class2);
        repository.classes().put(3L, class3);

        repository.students().put(1L, student1);
        repository.students().put(2L, student2);
        repository.students().put(3L, student3);
        repository.students().put(4L, student4);
        repository.students().put(5L, student5);
    }
}