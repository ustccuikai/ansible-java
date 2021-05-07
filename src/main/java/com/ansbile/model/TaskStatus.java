package com.ansbile.model;

public enum TaskStatus {

    QUEUE("QUEUE"),
    EXECUTING("EXECUTING"),
    FINALIZED("FINALIZED");

    private String status;

    TaskStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return this.status;
    }

}
