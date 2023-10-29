package com.example.demooauth;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


public interface Transactionrepo extends JpaRepository<Transaction, Integer> {
    List<Transaction> findByStudent_AcctNumOrderByCurrdateDesc(int acctNum);
}
