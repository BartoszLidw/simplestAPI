package com.example.sprigboot;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
class SprigbootApplicationTests {

    @Mock
    MappingClass repository;
    ObjectMapper mapper;
    MockMvc mockMvc;
    Controllers controller;
    Map<Long, ClassroomClass> classMap;
    Map<Long, StudentClass> studentMap;

    @BeforeEach
    void setUp() {
        mapper = new ObjectMapper();
        MockitoAnnotations.openMocks(this);
        controller = new Controllers(repository);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        classMap = new HashMap<>();
        studentMap = new HashMap<>();
    }
    @Test
    void addStudent() throws Exception {
        when(repository.students()).thenReturn(studentMap);
        StudentClass student = new StudentClass("xd", "xd", 1999, 0.2);
        mockMvc.perform(post("/api/student")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJsonBytes(student)))
                .andExpect(status().isOk());
        assertEquals(1, studentMap.size());
        verify(repository, times(1)).students();
    }

    @Test
    void deleteStudent() throws Exception {
        when(repository.students()).thenReturn(studentMap);
        StudentClass student = new StudentClass("xd", "xd",  1999, 0.2);
        studentMap.put(1L, student);
        assertEquals(1, studentMap.size());
        mockMvc.perform(delete("/api/student/1"))
                .andExpect(status().isOk());
        assertEquals(0, studentMap.size());
        verify(repository, times(1)).students();
    }

    @Test
    void getMeanOfGradesForStudent() throws Exception {
        double points = 0.9;
        when(repository.students()).thenReturn(studentMap);
        StudentClass student = new StudentClass("xd", "xd", 1999, points);
        studentMap.put(1L, student);
        assertEquals(1, studentMap.size());
        MvcResult result = mockMvc.perform(get("/api/student/1/grade"))
                .andExpect(status().isOk()).andReturn();
        assertEquals(points, Double.parseDouble(result.getResponse().getContentAsString()));
        verify(repository, times(1)).students();
    }

    @Test
    void getAllClasses() throws Exception {
        String name = "NaMeoFmyClaZz";
        when(repository.classes()).thenReturn(classMap);
        ClassroomClass myClass = new ClassroomClass(name, 12);
        classMap.put(1L, myClass);
        assertEquals(1, classMap.size());
        MvcResult result = mockMvc.perform(get("/api/course"))
                .andExpect(status().isOk()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains(name));
        verify(repository, times(1)).classes();
    }

    @Test
    void addCourse() throws Exception {
        when(repository.classes()).thenReturn(classMap);
        ClassroomClass myClass = new ClassroomClass("name", 12);
        assertEquals(0, classMap.size());
        MvcResult result = mockMvc.perform(post("/api/course")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJsonBytes(myClass)))
                .andExpect(status().isOk()).andReturn();
        assertEquals(1L, Long.parseLong(result.getResponse().getContentAsString()));
        assertEquals(1, classMap.size());
        verify(repository, times(1)).classes();
    }

    @Test
    void deleteCourse() throws Exception {
        when(repository.classes()).thenReturn(classMap);
        ClassroomClass myClass = new ClassroomClass("name", 12);
        classMap.put(1L, myClass);
        assertEquals(1, classMap.size());
        mockMvc.perform(delete("/api/course/1"))
                .andExpect(status().isOk());
        assertEquals(0, classMap.size());
        verify(repository, times(1)).classes();
    }

    @Test
    void getStudentsOfCourse() throws Exception {
        StudentClass student1 = new StudentClass ("Ricky", "xd1",  1999, 0.0);
        StudentClass  student2 = new StudentClass ("Julian", "xd2",  1993, 0.2);
        StudentClass  student3 = new StudentClass ("Bubbles", "xd3",  1992, 0.3);
        StudentClass  student4 = new StudentClass ("Jim", "xd3",  1992, 0.3);
        when(repository.classes()).thenReturn(classMap);
        ClassroomClass myClass = new ClassroomClass("name", 12);
        ClassroomClass myClass2 = new ClassroomClass("namasde", 12);
        myClass.addStudent(student1);
        myClass.addStudent(student2);
        myClass.addStudent(student3);
        myClass2.addStudent(student4);
        classMap.put(1L, myClass);
        classMap.put(2L, myClass2);
        MvcResult result = mockMvc.perform(get("/api/course/1/students"))
                .andExpect(status().isOk()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("Ricky"));
        assertTrue(result.getResponse().getContentAsString().contains("Julian"));
        assertTrue(result.getResponse().getContentAsString().contains("Bubbles"));
        assertFalse(result.getResponse().getContentAsString().contains("Jim"));
        verify(repository, times(1)).classes();
    }

    @Test
    void getFillOfClass() throws Exception {
        when(repository.classes()).thenReturn(classMap);
        StudentClass student1 = new StudentClass("Ricky", "xd1", 1999, 0.0);
        StudentClass student2 = new StudentClass("Julian", "xd2", 1993, 0.2);
        StudentClass student3 = new StudentClass("Bubbles", "xd3",1992, 0.3);
        ClassroomClass myClass = new ClassroomClass("name", 12);
        myClass.addStudent(student1);
        myClass.addStudent(student2);
        myClass.addStudent(student3);
        classMap.put(1L, myClass);
        MvcResult result = mockMvc.perform(get("/api/course/1/fill"))
                .andExpect(status().isOk()).andReturn();
        assertEquals(0.25, Double.parseDouble(result.getResponse().getContentAsString()));
        verify(repository, times(1)).classes();
    }

    @Test
    void getFillOfClassRainyDayScenario() throws Exception {
        when(repository.classes()).thenReturn(classMap);
        ClassroomClass myClass = new ClassroomClass("name", 12);
        classMap.put(1L, myClass);
        mockMvc.perform(get("/api/course/2/fill"))
                .andExpect(status().is4xxClientError());
        verify(repository, times(1)).classes();
    }

    @Test
    void addRatingForClass() throws Exception {
        RatingDTO ratingDTO = new RatingDTO(1L, 10.0);
        when(repository.students()).thenReturn(studentMap);
        StudentClass student = new StudentClass("Ricky", "xd1", 1999, 0.5);
        studentMap.put(1L, student);
        mockMvc.perform(post("/api/rating")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJsonBytes(ratingDTO)))
                .andExpect(status().isOk());
        assertEquals(10.5, student.getPoints());
        verify(repository, times(1)).students();
    }

    private byte[] convertObjectToJsonBytes(Object object) throws JsonProcessingException {
        return mapper.writeValueAsBytes(object);
    }
}
