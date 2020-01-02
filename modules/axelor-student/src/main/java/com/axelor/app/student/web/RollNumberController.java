package com.axelor.app.student.web;

import com.axelor.app.student.db.RollNumber;
import com.axelor.app.student.db.Student;
import com.axelor.app.student.db.repo.RollNumberRepository;
import com.axelor.inject.Beans;
import com.axelor.rpc.ActionRequest;
import com.axelor.rpc.ActionResponse;
import com.google.inject.persist.Transactional;

public class RollNumberController {

  @Transactional
  public void generateRollNumber(ActionRequest request, ActionResponse response) {
    Student stud = request.getContext().asType(Student.class);
    RollNumber rollNo = new RollNumber();
    rollNo = Beans.get(RollNumberRepository.class).save(rollNo);
//    rollNo=Beans.get(RollNumberRepository.class).all().filter("self.id = ?",rollNo.getId()).fetchOne();
    stud.setRollnumber(rollNo);
    response.setValues(stud);
//    if(true) {
//    }
//    System.err.println();
//    response.setAlert("Warning msg");
//    response.setReload(true);
  }
}
