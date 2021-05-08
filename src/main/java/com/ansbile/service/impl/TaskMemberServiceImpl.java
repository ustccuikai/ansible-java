package com.ansbile.service.impl;

import com.ansbile.model.TaskMember;
import com.ansbile.service.TaskMemberService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by cuikai on 2021/5/8.
 */
@Service
public class TaskMemberServiceImpl implements TaskMemberService {
    @Override
    public void addTaskMember(TaskMember taskMember) {

    }

    @Override
    public void updateTaskMember(TaskMember taskMember) {

    }

    @Override
    public TaskMember queryTaskMemberById(int id) {
        return null;
    }

    @Override
    public void updateTaskMemberLog(int id, String outputMsg, String errorMsg) {

    }

    @Override
    public List<TaskMember> queryTaskMemberByTaskStatus(int taskId, String taskStatus, int size) {
        return null;
    }

    @Override
    public int countTaskMemberByTaskStatus(int taskId, String taskStatus, int size) {
        return 0;
    }

    @Override
    public List<TaskMember> queryTaskMemberByTaskId(int taskId) {
        return null;
    }
}
