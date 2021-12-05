package com.example.entity;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
//@ToString(exclude = {"studnetSchool","studnetSchool","status"})
@ToString
public class Student {

    private int studentId;
    private String studentName;
    private String studentAddress;
    private String studnetSchool;
    private int status;
}
