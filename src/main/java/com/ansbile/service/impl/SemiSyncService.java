package com.ansbile.service.impl;

import com.ansbile.model.Task;
import com.ansbile.model.TaskMember;
import com.ansbile.service.DeploySchemaService;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
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

    //高可用组件 新建 or 复用
    @Override
    public List<TaskMember> buildTaskMembers(Task task) {
        List<TaskMember> taskMembers = new ArrayList<>();

        //参数通过两层map传递，第一层map标识组件的名称，包含mysql/orchestrator/prometheus/grafana
        //内存map表示配置的key和value
        Type type = new TypeToken<Map<String, Map<String, String>>>() {
        }.getType();
        Map<String, Map<String, String>> paramMap = new GsonBuilder().create().fromJson(task.getTaskParam(), type);


        //TODO:第三方组件复用和新安装的处理方式？

        //1. mysql5.7
        taskMembers.add(buildTaskMember(task, "21-mysql57", paramMap.get("mysql"), null));

        //2. orchestrator,复用已有集群需要增加--tags discover
        taskMembers.add(buildTaskMember(task, "22-orchestrator", paramMap.get("orchestrator"), null));

        //3. mysqld exporter
        taskMembers.add(buildTaskMember(task, "23-mysqld_exporter", null, null));

        //4. node exporter
        taskMembers.add(buildTaskMember(task, "03-node_exporter", null, null));

        //5. alert manager
        taskMembers.add(buildTaskMember(task, "04-alertmanager", null, null));

        //6. prometheus
        taskMembers.add(buildTaskMember(task, "05-prometheus", paramMap.get("prometheus"), null));

        //7. grafana
        taskMembers.add(buildTaskMember(task, "06-grafana", paramMap.get("grafana"), null));

        return taskMembers;
    }

    @Override
    public void postProcessor(Task task) {
        //将mysql集群信息写入mysql集群表

        /**
         * 第三方组件信息表，type区分类型
         * type: 1-orchestrator, 2-prometheus, 3-grafana
         */

    }
}
