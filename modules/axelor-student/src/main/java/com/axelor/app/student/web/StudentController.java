package com.axelor.app.student.web;

import com.axelor.app.AppSettings;
import com.axelor.app.student.db.Department;
import com.axelor.app.student.db.Student;
import com.axelor.app.student.db.Subject;
import com.axelor.app.student.service.StudentService;
import com.axelor.contact.db.Contact;
import com.axelor.db.JPA;
import com.axelor.rpc.ActionRequest;
import com.axelor.rpc.ActionResponse;
import com.google.inject.Inject;
import java.time.LocalDate;
import java.time.Period;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class StudentController {

  @Inject StudentService service;

  public void countStudentAge(ActionRequest request, ActionResponse response) {
    Student student = request.getContext().asType(Student.class);
    LocalDate bdate = student.getBdate();
    LocalDate today = LocalDate.now();
    Period p = Period.between(bdate, today);
    String home = service.getAttachmentPathForImage();
    home = System.getProperty("file.upload.dir");
    System.err.println("PATH: " + home);
    System.err.println("APP-PATH: " + AppSettings.get().getPath("file.upload.dir", ""));
    response.setValue("age", p.getYears());
  }

  public String getHomePath(ActionRequest request, ActionResponse response) {
    String home = service.getAttachmentPathForImage();
    //		home = System.getProperty("user.home") + "/.axelor/attachments/";
    System.err.println("HOME-PATH: " + home);
    AppSettings.get().getPath("file.upload.dir", "a");
    return home;
  }

  public Object validateStudentData(Object bean, Map<String, Object> context) {
    assert bean instanceof Student;
    Student student = (Student) bean;

    LocalDate bdate = student.getBdate();
    LocalDate today = LocalDate.now();
    Period p = Period.between(bdate, today);

    Department department = student.getDepartment();
    Set<Subject> set = department.getSubject().stream().collect(Collectors.toSet());
    student.setSubject(set);
    student.setAge(p.getYears());
    service.validateStudent(student);

    System.err.println("Name: " + student.getName());
    System.err.println("Student: " + student.getRollnumber());
    System.err.println("Age: " + student.getAge());

    long count = JPA.all(Contact.class).count();
    assert count > 1;

    return bean;
  }
}
