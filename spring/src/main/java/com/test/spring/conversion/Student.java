package com.test.spring.conversion;

import com.test.spring.beanFactoryPostProcessor.StudentService;

public class Student {
    private StudentService studentService;

    public StudentService getStudentService() {
        return studentService;
    }

    public void setStudentService(StudentService studentService) {
        this.studentService = studentService;
    }
}
