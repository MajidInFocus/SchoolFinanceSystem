package org.schoolFinancialSystem;

public class Transaction {
    private int studentId;
    private int fees;
    private int paidFees;

    public Transaction(int studentId, int fees, int paidFees) {
        this.studentId = studentId;
        this.fees = fees;
        this.paidFees = paidFees;
    }

    public int getStudentId() {
        return studentId;
    }

    public int getFees() {
        return fees;
    }

    public int getPaidFees() {
        return paidFees;
    }
}

