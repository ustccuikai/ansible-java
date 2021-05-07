package com.ansbile.model;

public enum TaskStopType {

    DEFAULT(0),
    // 正常完成停止任务
    COMPLETE_STOP(1),
    // 超时停止任务
    TIMEOUT_STOP(2),
    // 子任务强制停止
    MEMBER_TASK_STOP(3),
    // 主任务强制停止
    TASK_STOP(4);

    private int type;

    TaskStopType(int type) {
        this.type = type;
    }

    public int getType() {
        return this.type;
    }
}
