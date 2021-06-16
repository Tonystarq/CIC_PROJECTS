package com.example.myapplication.EventBus;


// 0 for Year
//1 for semester


public class SheetClick {
    private boolean success;
    private String string;
    int operation;

    public SheetClick(boolean success, int operation, String string) {
        this.success = success;
        this.string = string;
        this.operation = operation;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }

    public int getOperation() {
        return operation;
    }

    public void setOperation(int operation) {
        this.operation = operation;
    }
}