package com.ansbile.service;

import com.ansbile.model.Task;
import com.ansbile.model.TaskMember;

import java.util.List;
import java.util.Map;

public interface DeploySchemaService {

    List<TaskMember> buildTaskMembers(Task task, Map<String, String> groupHostsMap);

}
