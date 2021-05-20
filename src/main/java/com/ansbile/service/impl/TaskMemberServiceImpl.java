package com.ansbile.service.impl;

import com.ansbile.dao.entity.TaskMember;
import com.ansbile.dao.reposity.TaskMemberRepository;
import com.ansbile.service.TaskMemberService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

/**
 * Created by cuikai on 2021/5/8.
 */
@Service
public class TaskMemberServiceImpl implements TaskMemberService {

    @Resource
    private TaskMemberRepository repository;

    @Override
    public void addTaskMember(TaskMember taskMember) {
        repository.save(taskMember);
    }

    @Override
    public void updateTaskMember(TaskMember taskMember) {
        repository.save(taskMember);
    }

    @Override
    public TaskMember queryTaskMemberById(long id) {
        Optional<TaskMember> task = repository.findById(id);
        return task.orElse(null);
    }

    @Override
    public void updateTaskMemberLog(long id, String outputMsg, String errorMsg) {
        TaskMember taskMember = queryTaskMemberById(id);
        taskMember.setOutputMsg(outputMsg);
        taskMember.setErrorMsg(errorMsg);

        repository.save(taskMember);
    }

    @Override
    public List<TaskMember> queryTaskMemberByTaskStatus(long taskId, String taskStatus, int size) {
        return null;
    }

    @Override
    public int countTaskMemberByTaskStatus(long taskId, String taskStatus, int size) {
        return 0;
    }
}
