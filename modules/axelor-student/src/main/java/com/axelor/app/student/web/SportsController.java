package com.axelor.app.student.web;

import java.util.Map;

import com.axelor.app.student.db.Sports;
import com.axelor.db.JPA;
import com.axelor.db.JpaSupport;

public class SportsController extends JpaSupport {

	public Object checkForStudentBinding(Object bean, Map<String, Object> context) {
		assert bean instanceof Sports;
		Sports sp = (Sports) bean;

//		Student s = JPA.all(Student.class).filter("self.name = ?",sp.getStudent()).fetchOne();

		long count = JPA.all(Sports.class).count();
		System.err.println("COUNT-SPORTS: " + count);
		assert count > 1;

		return bean;
	}
}
