package com.ansbile.service;

import com.ansbile.model.Task;

public interface TaskService {

    void addTask(Task task);

    void updateTask(Task task);

    Task queryTaskById(int id);
}
