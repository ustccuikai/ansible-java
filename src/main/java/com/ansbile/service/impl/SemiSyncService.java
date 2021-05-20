package com.ansbile.service.impl;

import com.ansbile.model.AnsibleGroup;
import com.ansbile.model.AnsibleInventory;
import com.ansbile.dao.entity.Task;
import com.ansbile.dao.entity.TaskMember;
import com.ansbile.service.DeploySchemaService;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by cuikai on 2021/5/7.
 */
@Service("SemiSync")
public class SemiSyncService extends DeploySchemaService {

    @Override
    public List<TaskMember> buildTaskMembers(Task task) {
        List<TaskMember> taskMembers = new ArrayList<>();

        //参数通过两层map传递，第一层map标识组件的名称，包含mysql/orchestrator
        //内存map表示配置的key和value
        Type type = new TypeToken<Map<String, Map<String, String>>>() {
        }.getType();
        Map<String, Map<String, String>> paramMap = new GsonBuilder().create().fromJson(task.getTaskParam(), type);

        //1. mysql5.7
        taskMembers.add(buildTaskMember(task, "21-mysql57", paramMap.get("mysql"), null));

        //2. orchestrator,复用已有集群需要增加--tags discover
        if (StringUtils.isBlank(task.getOrchClusterId())) {
            taskMembers.add(buildTaskMember(task, "22-orchestrator", paramMap.get("orchestrator"), null));
        } else {
            taskMembers.add(buildTaskMember(task, "22-orchestrator", paramMap.get("orchestrator"), "discover"));
        }

        //3. mysqld exporter
        taskMembers.add(buildTaskMember(task, "23-mysqld_exporter", null, null));

        //4. node exporter
        taskMembers.add(buildTaskMember(task, "03-node_exporter", null, null));

        //TODO:5. 配置监控

        return taskMembers;
    }

    @Override
    public void postProcessor(Task task) {
        AnsibleInventory inventory = new GsonBuilder().create().fromJson(
                task.getInventoryJson(), AnsibleInventory.class);
        AnsibleGroup orchestratorGroup = null;
        AnsibleGroup mysqlGroup = null;
        for (AnsibleGroup group : inventory.getGroups()) {
            if (StringUtils.equals(group.getName(), "orchestrator")) {
                orchestratorGroup = group;
            }
            if (StringUtils.equals(group.getName(), "semiservers")) {
                mysqlGroup = group;
            }
        }

        //将mysql集群信息写入mysql集群表
        if (mysqlGroup != null) {

        }


        /**
         * 第三方组件信息表，type区分类型
         * type: 1-orchestrator集群
         */
        if (StringUtils.isBlank(task.getOrchClusterId())) {

        }
    }
}
