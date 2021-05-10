package com.ansbile.service;

import com.ansbile.model.Task;
import com.ansbile.model.TaskMember;

import java.util.List;

public interface DeploySchemaService {

    //orchestrator复用与否生成的ansible的任务不一样
    List<TaskMember> buildTaskMembers(Task task);

    //安装成功后执行后置处理
    void postProcessor(Task task);
}
