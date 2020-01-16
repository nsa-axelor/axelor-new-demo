package com.axelor.app.student.service;

import com.axelor.app.AppSettings;
import com.axelor.app.student.db.Student;
import java.time.LocalDate;
import java.time.Period;

public class StudentServiceImpl implements StudentService {

  @Override
  public void validateStudent(Student student) {
    LocalDate bdate = student.getBdate();
    LocalDate today = LocalDate.now();
    Period p = Period.between(bdate, today);
    // response.setValue("age", p.getYears());
    if (p.getYears() < 17) {
      System.err.println("Student age is less than 17!");
    }
  }

  //	@Override
  public String getAttachmentPathForImage() {
    String homePath = System.getProperty("user.home") + "/.axelor/attachments/";
    System.err.println("HOME-PATH: " + homePath);
    System.err.println("APP-PATH: " + AppSettings.get().getPath("file.upload.dir", ""));
    return homePath;
  }
}
