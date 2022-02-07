package com.example.sprigboot;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class MappingClass {

    private Map<Long, ClassroomClass> classes;
    private Map<Long, StudentClass> students;

    public MappingClass() {
        classes = new HashMap<>();
        students = new HashMap<>();
    }

    public Map<Long, ClassroomClass> classes() {
        return classes;
    }

    public Map<Long, StudentClass> students() {
        return students;
    }
}

