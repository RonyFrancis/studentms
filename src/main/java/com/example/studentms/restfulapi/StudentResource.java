// This is a RESTful API for managing students using Spring Boot and Spring Data JPA
package com.example.studentms.restfulapi;

// Import the necessary classes and annotations
import com.example.studentms.domain.Student;
import com.example.studentms.repo.StudentRepo;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.beans.PropertyDescriptor;
import java.util.List;

// Annotate the class as a REST controller
@RestController
public class StudentResource {

    // Autowire the student repository to access the database
    @Autowired
    private StudentRepo studentRepo;

    // A simple method to test the API by returning "hello world"
    // It handles GET requests to /hello
    @GetMapping("/hello")
    public String hello() {
        return "hello world";
    }

    // A method to get all the students from the database
    // It handles GET requests to /students
    // It returns a list of student objects
    @GetMapping("/students")
    public List<Student> getAllUsers() {
        return studentRepo.findAll();
    }

    // A method to get a specific student by id from the database
    // It handles GET requests to /student/{id}
    // It takes the id as a path variable
    // It returns the student object or null if not found
    @GetMapping("/student/{id}")
    public Student getStudentById(@PathVariable Integer id) {
        return studentRepo.findById(id).orElse(null);
    }

    // A method to create a new student in the database
    // It handles POST requests to /student
    // It takes the student object as a request body
    // It returns the saved student object
    @PostMapping("/student")
    public Student createStudent(@RequestBody Student student){
        return studentRepo.save(student);
    }

    // A method to update an existing student in the database
    // It handles PUT requests to /student/{id}
    // It takes the id as a path variable and the student object as a request body
    // It returns the updated student object or null if not found
    @PutMapping("/student/{id}")
    public Student updateStudent(@PathVariable Integer id, @RequestBody Student student) {
        // Find the existing student by id
        Student existingStudent = studentRepo.findById(id).orElse(null);
        if (existingStudent == null) {
            // No student found, return null
            return null;
        }
        // Copy the non-null properties from the request body to the existing student
        copyNonNullProperties(student, existingStudent);
        // Save the updated student and return it
        return studentRepo.save(existingStudent);
    }

    // A method to delete an existing student from the database
    // It handles DELETE requests to /student/{id}
    // It takes the id as a path variable
    // It returns a message indicating the deletion
    @DeleteMapping("/student/{id}")
    public String deleteStudent(@PathVariable Integer id) {
        // Delete the student by id
        studentRepo.deleteById(id);
        // Return a message
        return "student has been deleted";
    }

    // A helper method to copy non-null properties from one object to another
    // It takes the source and target objects as parameters
    private void copyNonNullProperties(Object source, Object target) {
        // Get the property descriptors of the source and target objects
        BeanWrapper src = new BeanWrapperImpl(source);
        BeanWrapper trg = new BeanWrapperImpl(target);

        // Loop through the source properties
        for (PropertyDescriptor pd : src.getPropertyDescriptors()) {
            // Check if the property value is not null and is writable
            if (src.getPropertyValue(pd.getName()) != null && pd.getWriteMethod() != null) {
                // Copy the property value to the target object
                trg.setPropertyValue(pd.getName(), src.getPropertyValue(pd.getName()));
            }
        }
    }
}
