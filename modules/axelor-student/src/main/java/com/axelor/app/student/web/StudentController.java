package com.axelor.app.student.web;

import com.axelor.app.student.db.Student;
import com.axelor.rpc.ActionRequest;
import com.axelor.rpc.ActionResponse;
import java.time.LocalDate;
import java.time.Period;

public class StudentController {

  public void countStudentAge(ActionRequest request, ActionResponse response) {
    Student student = request.getContext().asType(Student.class);
    LocalDate bdate = student.getBdate();
    LocalDate today = LocalDate.now();
    Period p = Period.between(bdate, today);
    response.setValue("age", p.getYears());
  }
}
