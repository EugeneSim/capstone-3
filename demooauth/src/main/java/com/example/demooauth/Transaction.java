package com.example.demooauth;


import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int transactionNum;

    private boolean acctOpen;

    private String actionPerformed;

    private double amount;
    private double balanceBefore;
    private double balanceAfter;

   
    private LocalDate currdate;

    @ManyToOne
    @JoinColumn(name = "acctNum") // Foreign key referencing Customer entity
    private Student student;

    public Transaction() {
    }

    
}
