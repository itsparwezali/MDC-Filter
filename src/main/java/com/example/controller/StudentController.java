package com.example.controller;



import com.example.constant.ApiConstant;
import com.example.entity.Status;
import com.example.entity.Student;
import com.example.response.ServerResponse;
import com.example.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = ApiConstant.student)
public class StudentController {

    @Autowired
    private StudentService studentService;


    @PostMapping(value = ApiConstant.save)
    public ResponseEntity<?> save(@RequestBody Student student){
        System.out.println("======"+student);
        ServerResponse<Student> serverResponse = studentService.save(student);
        return new ResponseEntity<>(serverResponse, serverResponse.getHttpStatus());

    }


    @GetMapping(value = ApiConstant.get_all)
    public ResponseEntity<?> getAllStudent(){
        System.out.println("working");
        ServerResponse<List<Student>> serverResponse = studentService.getAll();
        return new ResponseEntity<>(serverResponse,serverResponse.getHttpStatus());
    }

    @GetMapping(value = ApiConstant.student_id)
    public ResponseEntity<?> getStudentById(@PathVariable("id") int id){
        ServerResponse<Student> serverResponse = studentService.findbyStudentId(id);
        return new ResponseEntity<>(serverResponse,serverResponse.getHttpStatus());
    }

    @PutMapping(value = ApiConstant.student_id)
    public ResponseEntity<?> updateStudent(@PathVariable("id") int id, @RequestBody Status status){
        ServerResponse<Student> serverResponse = studentService.updateStudentById(id,status);
        return new ResponseEntity<>(serverResponse,serverResponse.getHttpStatus());
    }


}
