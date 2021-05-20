package com.ansbile.service.impl;

import com.ansbile.dao.entity.Task;
import com.ansbile.dao.entity.TaskMember;
import com.ansbile.service.DeploySchemaService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by cuikai on 2021/5/7.
 */
@Service("SemiSyncHA")
public class SemiSyncHAService extends DeploySchemaService {

    @Override
    public List<TaskMember> buildTaskMembers(Task task) {
        return null;
    }

    @Override
    public void postProcessor(Task task) {

    }
}
