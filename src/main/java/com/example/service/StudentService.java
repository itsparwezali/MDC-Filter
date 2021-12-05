package com.example.service;


import com.example.entity.Status;
import com.example.entity.Student;
import com.example.exception.BadRequestException;
import com.example.exception.ResourceNotFoundException;
import com.example.response.ResponseUtility;
import com.example.response.ServerResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
@Slf4j
public class StudentService {

    @Autowired
    Deb deb;

    private static ArrayList<Student> arrayList = new ArrayList<>();

    static {
        arrayList.add(new Student(11,"parwez ali","biratnagar","orchid",1));
        arrayList.add(new Student(22,"Nasim aktar","janakpur","arniko",0));

    }
    public ServerResponse<Student> save(Student student) {
        boolean result = arrayList.add(student);

        log.info("save successfully.....");
        if (result) {
            return ResponseUtility.getCreatedServerResponse("successfully added new student");
        }
        throw new BadRequestException("bad request ho yo.....");

    }


    public ServerResponse<List<Student>> getAll(){
        if (arrayList.isEmpty()){
            throw new ResourceNotFoundException("student are not available in list");
        }
        deb.go("hi bro...");
        log.info("fetch successfull..");
     return ResponseUtility.getSuccessfulServerResponseDTO(arrayList,"list of student");
    }

    public ServerResponse<Student> findbyStudentId(int id){

        Student student = arrayList.stream().filter(e -> e.getStudentId() == id).findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("student id not found"));
        return ResponseUtility.getSuccessfulServerResponseDTO(student,"successfull...!!!");
    }
    public ServerResponse<Student> updateStudentById(int id, Status status){

        Student student1 = arrayList.stream().filter(e -> e.getStudentId() == id).findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("student id not found student"));
        student1.setStatus(status.getStatus());

        return ResponseUtility.getUpdatedServerResponse("update successfully...!!!");
    }

}