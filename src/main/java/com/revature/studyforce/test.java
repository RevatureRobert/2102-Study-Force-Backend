package com.revature.studyforce;

import com.revature.studyforce.user.model.Authority;
import com.revature.studyforce.user.model.Batch;
import com.revature.studyforce.user.model.User;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class test {
  public static void main(String[] args) {
      Set<User> AdminList = new HashSet<>();
      Set<User> StudentList = new HashSet<>();
      List<Batch> BatchList = new ArrayList<>();

      Authority authority = Authority.ADMIN;
      Authority user1 = Authority.USER;
      Timestamp lastLoginTime = Timestamp.valueOf ("2021-04-30 11:00:01");
      User Admin = new User(1 , "dan@gmail.com", "pass", "Daniel", true, true, true, authority, lastLoginTime, lastLoginTime);
      User student = new User(2 , "test@gmail.com", "pass", "Danny", true, true, true, user1, lastLoginTime, lastLoginTime);
      AdminList.add(Admin);
      StudentList.add(student);  //
      Batch batch = new Batch(1, "2102 Enterprise", AdminList, StudentList, lastLoginTime);
      BatchList.add(batch);
    System.out.println(batch.toString());
    System.out.println(batch.getBatchId());
    System.out.println(batch.getName());
    System.out.println(batch.getUsers().toString());
  }
}
