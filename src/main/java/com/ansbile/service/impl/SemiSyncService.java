package com.ansbile.service.impl;

import com.ansbile.model.Task;
import com.ansbile.model.TaskMember;
import com.ansbile.service.DeploySchemaService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cuikai on 2021/5/7.
 */
@Service("SemiSync")
public class SemiSyncService extends DeploySchemaService {

    //高可用组件 新建 or 复用
    @Override
    public List<TaskMember> buildTaskMembers(Task task) {
        List<TaskMember> taskMembers = new ArrayList<>();

        //TODO:解析参数，分拆到每个子任务中

        //TODO:第三方组件复用和新安装的处理方式？

        //1. mysql5.7
        taskMembers.add(buildTaskMember(task, "21-mysql57"));

        //2. orchestrator,复用已有集群需要增加--tags discover
        taskMembers.add(buildTaskMember(task, "22-orchestrator"));

        //3. mysqld exporter
        taskMembers.add(buildTaskMember(task, "23-mysqld_exporter"));

        //4. node exporter
        taskMembers.add(buildTaskMember(task, "03-node_exporter"));

        //5. alert manager
        taskMembers.add(buildTaskMember(task, "04-alertmanager"));

        //6. prometheus
        taskMembers.add(buildTaskMember(task, "05-prometheus"));

        //7. grafana
        taskMembers.add(buildTaskMember(task, "06-grafana"));

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
