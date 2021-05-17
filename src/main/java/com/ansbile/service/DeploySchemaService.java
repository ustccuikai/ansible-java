package com.ansbile.service;

import com.ansbile.model.Task;
import com.ansbile.model.TaskMember;

import java.util.List;

public abstract class DeploySchemaService {

    public abstract List<TaskMember> buildTaskMembers(Task task);

    //安装成功后执行后置处理
    public abstract void postProcessor(Task task);

    public TaskMember buildTaskMember(Task task, String playbookName, String param, String tags) {
        return TaskMember.builder()
                .playbookName(playbookName)
                .playbookTags(tags)
                .executorParam(param)
                .inventoryJson(task.getInventoryJson()).build();
    }

    public TaskMember buildTaskMember(Task task, String playbookName) {
       return buildTaskMember(task, playbookName, null, null);
    }
}
