package com.example.sprigboot;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString


    public class StudentClass extends MappingClass implements Comparable<StudentClass>, Serializable {

        private String firstName;
        private String lastName;
        private int birthYear;
        private double points;

        public void addPoints(double points) {
            this.points += points;
        }

        public void print() {
            System.out.println(this);
        }

        @Override
        public int compareTo(StudentClass o) {
            return lastName.compareTo(o.lastName);
        }
    }

