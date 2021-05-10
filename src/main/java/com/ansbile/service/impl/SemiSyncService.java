package com.ansbile.service.impl;

import com.ansbile.model.Task;
import com.ansbile.model.TaskMember;
import com.ansbile.service.DeploySchemaService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by cuikai on 2021/5/7.
 */
@Service("SemiSync")
public class SemiSyncService implements DeploySchemaService {

    //高可用组件 新建 or 复用
    @Override
    public List<TaskMember> buildTaskMembers(Task task) {
        return null;
    }

    @Override
    public void postProcessor(Task task) {
        //将mysql集群信息写入mysql集群表

        //将orchestrator集群信息写入orchestrator集群表

    }
}
