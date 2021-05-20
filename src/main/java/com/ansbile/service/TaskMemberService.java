package com.ansbile.service;


import com.ansbile.dao.entity.TaskMember;

import java.util.List;

public interface TaskMemberService {

    void addTaskMember(TaskMember taskMember);

    void updateTaskMember(TaskMember taskMember);

    TaskMember queryTaskMemberById(long id);

    void updateTaskMemberLog(long id, String outputMsg, String errorMsg);

    List<TaskMember> queryTaskMemberByTaskStatus(long taskId, String taskStatus, int size);

    int countTaskMemberByTaskStatus(long taskId, String taskStatus, int size);
}
