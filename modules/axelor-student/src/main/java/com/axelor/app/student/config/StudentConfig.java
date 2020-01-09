package com.axelor.app.student.config;

import com.axelor.app.AxelorModule;
import com.axelor.app.student.service.StudentService;
import com.axelor.app.student.service.StudentServiceImpl;

public class StudentConfig extends AxelorModule {

  @Override
  protected void configure() {
    bind(StudentService.class).to(StudentServiceImpl.class);
  }
}
