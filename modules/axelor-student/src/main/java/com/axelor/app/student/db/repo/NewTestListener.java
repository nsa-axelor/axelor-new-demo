package com.axelor.app.student.db.repo;

import com.axelor.app.student.db.NewTest;
import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;

public class NewTestListener {
  @PostPersist
  @PostUpdate
  private void onPostPersistOrUpdate(NewTest newtest) {
    System.err.println("New Test Saved");
  }
}
