package com.ansbile.service.impl;

import com.ansbile.model.Task;
import com.ansbile.model.TaskMember;
import com.ansbile.service.DeploySchemaService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by cuikai on 2021/5/7.
 */
@Service("SemiSyncHA")
public class SemiSyncHAService implements DeploySchemaService {

    @Override
    public List<TaskMember> buildTaskMembers(Task task, Map<String, String> hostPatternMap) {
        return null;
    }
}
