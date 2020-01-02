package com.axelor.app.student.web;

import com.axelor.app.student.db.Student;
import com.axelor.rpc.ActionRequest;
import com.axelor.rpc.ActionResponse;

public class StudentController {

  public void setStudentAge(ActionRequest request, ActionResponse response) {
    Student student = request.getContext().asType(Student.class);
  }
}
