package com.ansbile.service;


import com.ansbile.dao.entity.TaskMember;

import java.util.List;

public interface TaskMemberService {

    void addTaskMember(TaskMember taskMember);

    void updateTaskMember(TaskMember taskMember);

    TaskMember queryTaskMemberById(int id);

    void updateTaskMemberLog(int id, String outputMsg, String errorMsg);

    List<TaskMember> queryTaskMemberByTaskStatus(int taskId, String taskStatus, int size);

    int countTaskMemberByTaskStatus(int taskId, String taskStatus, int size);
}
