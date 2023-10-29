package com.example.demooauth;

import org.springframework.data.jpa.repository.JpaRepository;

public interface Studentrepo extends JpaRepository<Student, Integer> {
    Student findByUserId(String userId);
    Student findByAcctNum(int acctNum);


}
