package com.ansbile.service.impl;

import com.ansbile.dao.entity.Task;
import com.ansbile.dao.reposity.TaskRepository;
import com.ansbile.service.TaskService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;


@Service
public class TaskServiceImpl implements TaskService {

    @Resource
    private TaskRepository repository;

    @Override
    public void addTask(Task task) {
        repository.save(task);
    }

    @Override
    public void updateTask(Task task) {
        repository.save(task);
    }

    @Override
    public Task queryTaskById(long id) {
        Optional<Task> task = repository.findById(id);
        return task.orElse(null);
    }
}
