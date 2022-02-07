package com.example.sprigboot;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api")
public class Controllers {

    private final MappingClass repository;
    public Controllers(MappingClass repository) {
        this.repository = repository;
    }

    @PostMapping("/student")
    public ResponseEntity<Long> addStudent(@RequestBody StudentClass student) {
        Map<Long, StudentClass> students = repository.students();
        Long id = (long) (students.size() + 1);
        students.put(id, student);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/student/{id}")
    public ResponseEntity<StudentClass> deleteStudent(@PathVariable Long id) {
        return ResponseEntity.ok(repository.students().remove(id));
    }

    @GetMapping("/student/{id}/grade")
    public ResponseEntity<Double> getMeanOfGradesForStudent(@PathVariable Long id) {
        StudentClass student = repository.students().get(id);
        if (student == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(0.0);
        return ResponseEntity.status(HttpStatus.OK).body(student.getPoints());
    }

    @GetMapping("/course")
    public ResponseEntity<Collection<ClassroomClass>> getAllClasses() {
        return ResponseEntity.ok(repository.classes().values());
    }

    @PostMapping("/course")
    public ResponseEntity<Long> addCourse(@RequestBody ClassDTO classDTO) {
        Map<Long, ClassroomClass> classes = repository.classes();
        Long id = (long) (classes.size() + 1);
        ClassroomClass myClass = new ClassroomClass(classDTO.getName(),
                classDTO.getStudentsLimit());
        classes.put(id, myClass);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/course/{id}")
    public ResponseEntity<ClassroomClass> deleteCourse(@PathVariable Long id) {
        return ResponseEntity.ok(repository.classes().remove(id));
    }

    @GetMapping("/course/{id}/students")
    public ResponseEntity<List<StudentClass>> getStudentsOfCourse(@PathVariable Long id) {
        ClassroomClass myClass = repository.classes().get(id);
        if (myClass == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(myClass.getStudents());
    }

    @GetMapping("/course/{id}/fill")
    public ResponseEntity<Double> getFillOfClass(@PathVariable Long id) {
        ClassroomClass myClass = repository.classes().get(id);
        if (myClass == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok((double) myClass.getNumberOfStudents() /
                (double) myClass.getStudentLimit() );
    }

    @PostMapping("/rating")
    public ResponseEntity<String> addRatingForClass(@RequestBody RatingDTO ratingDTO) {
        repository.students().get(ratingDTO.getStudentId()).addPoints(ratingDTO.getRating());
        return ResponseEntity.status(HttpStatus.OK).body("Added rating to student.");
    }
}
