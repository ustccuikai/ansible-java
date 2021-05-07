package com.ansbile.service;


import com.ansbile.model.TaskMember;

import java.util.List;

public interface TaskMemberService {

    void addTaskMember(TaskMember taskMember);

    void updateTaskMember(TaskMember taskMember);

    TaskMember queryTaskMemberById(int id);

    List<TaskMember> queryTaskMemberByTaskStatus(int taskId, String taskStatus, int size);

    int countTaskMemberByTaskStatus(int taskId, String taskStatus, int size);

    List<TaskMember> queryTaskMemberByTaskId(int taskId);
}
