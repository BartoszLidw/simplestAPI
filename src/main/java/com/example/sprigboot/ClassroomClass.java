package com.example.sprigboot;

import lombok.ToString;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@ToString
public class ClassroomClass extends MappingClass {

    private String name;
    private final List<StudentClass> students = new ArrayList<>();
    private long studentsLimit;

    public ClassroomClass(String name, int maxStudentNumber) {
        this.name = name;
        this.studentsLimit = maxStudentNumber;
    }

    public ClassroomClass(String name, List<StudentClass> students, int maxStudentNumber) {
        this.name = name;
        this.students.addAll(students);
        this.studentsLimit = maxStudentNumber;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStudentsLimit(long studentsLimit) {
        this.studentsLimit = studentsLimit;
    }

    public void addStudent(StudentClass student) {
        if (students.size() < studentsLimit) {
            if (!students.contains(student)) {
                students.add(student);
            } else {
                System.err.println("Student already in class");
            }
        } else {
            System.err.println("Too many class members.");
        }
    }

    public StudentClass getStudentAt(int id) {
        return students.get(id);
    }

    public List<StudentClass> getStudents() {
        return students;
    }

    public void addPoints(StudentClass student, double points) {
        students.get(students.indexOf(student)).addPoints(points);
    }

    public Optional<StudentClass> takeStudent(StudentClass student) {
        return Optional.ofNullable(students.get(students.indexOf(student)));
    }

    public void removeStudentAt(int id) {
        students.remove(id);
    }

    public Optional<StudentClass> findStudent(String lastName) {
        return students.stream()
                .filter(s -> s.getLastName().equals(lastName))
                .findFirst();
    }

    public List<StudentClass> findPartial(String firstNameOrLastName) {
        return students.stream()
                .filter(s -> s.getFirstName().contains(firstNameOrLastName)
                        || s.getLastName().contains(firstNameOrLastName))
                .toList();
    }



    public void summary() {
        students.forEach(StudentClass::print);
    }

    public void sortBy(Function<StudentClass, Comparable> function) {
        students.sort(Comparator.comparing(function));
    }

    public StudentClass max() {
        return Collections.max(students);
    }

    public long getNumberOfStudents() {
        return students.size();
    }

    public long getStudentLimit() {
        return studentsLimit;
    }

    public String getName() {
        return name;
    }
}
