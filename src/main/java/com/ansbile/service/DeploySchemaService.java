package com.ansbile.service;

import com.ansbile.model.Task;
import com.ansbile.model.TaskMember;

import java.util.List;

public interface DeploySchemaService {

    List<TaskMember> buildTaskMembers(Task task);

}
