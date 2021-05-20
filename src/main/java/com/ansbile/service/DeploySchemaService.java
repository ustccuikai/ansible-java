package com.ansbile.service;

import com.ansbile.dao.entity.Task;
import com.ansbile.dao.entity.TaskMember;
import com.google.gson.GsonBuilder;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;

public abstract class DeploySchemaService {

    public abstract List<TaskMember> buildTaskMembers(Task task);

    //安装成功后执行后置处理
    public abstract void postProcessor(Task task);

    public TaskMember buildTaskMember(Task task, String playbookName,
                                      Map<String, String> paramMap, String tags) {
        TaskMember.TaskMemberBuilder builder = TaskMember.builder()
                .playbookName(playbookName)
                .inventoryJson(task.getInventoryJson());

        if (StringUtils.isNotBlank(tags)) {
            builder.playbookTags(tags);
        }
        if (MapUtils.isNotEmpty(paramMap)) {
            builder.executorParam(new GsonBuilder().create().toJson(paramMap));
        }

        return builder.build();
    }
}
