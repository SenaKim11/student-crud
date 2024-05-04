package com.sena.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

@Controller // This means that this class is a Controller
@RequestMapping(path="/student") // This means URL's start with /demo (after Application path)
public class MainController {
    @Autowired // This means to get the bean called userRepository
    // Which is auto-generated by Spring, we will use it to handle the data
    private StudentRepository studentRepository;

    @GetMapping
    public @ResponseBody Iterable<Student> getStudents() {
        // This returns a JSON or XML with the users
        return studentRepository.findAll();
    }

    @GetMapping(path="/{id}") // Map ONLY POST Requests
    public @ResponseBody Optional<Student> getStudent (@PathVariable Integer id) {
        return studentRepository.findById(id);
    }

    @DeleteMapping(path="/{id}") // Map ONLY POST Requests
    public @ResponseBody String deleteStudent (@PathVariable Integer id) {

        String response;
        try {
            studentRepository.deleteById(id);
            response = "Student with id %d deleted.".formatted(id);
        } catch (Exception e) {
            response = "No student found.";
        }
        return response;
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity updateStudent(@PathVariable Integer id, @RequestBody Student student) {
        Student currentStudent = studentRepository.findById(id).orElseThrow(RuntimeException::new);
        currentStudent.setFirstName(student.getFirstName());
        currentStudent.setLastName(student.getLastName());
        currentStudent.setCourse(student.getCourse());
        currentStudent.setStudentNumber(student.getStudentNumber());
        currentStudent = studentRepository.save(student);

        return ResponseEntity.ok(currentStudent);
    }

    @PostMapping
    public ResponseEntity createStudent(@RequestBody Student student) throws URISyntaxException {
        Student savedStudent = studentRepository.save(student);
        return ResponseEntity.created(new URI("/students/" + savedStudent.getId())).body(savedStudent);
    }

    @PostMapping(path = "/new")
    public @ResponseBody String addStudent (
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String course,
            @RequestParam String studentNumber ) {

        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request
        Student s = new Student();

        s.setFirstName(firstName);
        s.setLastName(lastName);
        s.setCourse(course);
        s.setStudentNumber(studentNumber);

        String response;
        try {
            Student st = studentRepository.save(s);
            response = "Success adding car with id: " + st.getId();
        } catch (Exception e) {
            response =  "Error " + e.getMessage();
        }
        return response;
    }

}