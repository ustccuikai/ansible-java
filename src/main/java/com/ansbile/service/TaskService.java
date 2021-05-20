package com.ansbile.service;

import com.ansbile.dao.entity.Task;

public interface TaskService {

    void addTask(Task task);

    void updateTask(Task task);

    Task queryTaskById(long id);
}
