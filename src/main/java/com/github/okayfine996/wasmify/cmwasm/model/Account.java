package com.github.okayfine996.wasmify.cmwasm.model;


public class Account {
    private int accountNumber;
    private int sequence;

    public Account(int accountNumber, int sequence) {
        this.accountNumber = accountNumber;
        this.sequence = sequence;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }
}
