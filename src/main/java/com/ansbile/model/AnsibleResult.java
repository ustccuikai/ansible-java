package com.ansbile.model;

public enum AnsibleResult {

    SUCCESSFUL(0, "SUCCESSFUL"),
    ERROR(1, "ERROR");

    private int type;
    private String name;

    AnsibleResult(int type, String name) {
        this.type = type;
        this.name = name;
    }

    public int getType() {
        return this.type;
    }

    public String getName() {
        return this.name;
    }

    public static String getName(int type) {
        for (AnsibleResult result : AnsibleResult.values())
            if (result.getType() == type)
                return result.getName();
        return "FAILED";
    }
}
