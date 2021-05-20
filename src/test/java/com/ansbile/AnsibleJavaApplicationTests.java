package com.ansbile;

import com.ansbile.dao.entity.TaskMember;
import com.ansbile.exec.AnsibleExecutorHandler;
import com.ansbile.model.*;
import com.ansbile.config.AnsiblePlaybookArgs;
import com.ansbile.service.TaskMemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AnsibleJavaApplicationTests {

    @Autowired
    AnsibleExecutorHandler ansibleExecutorHandler;

    @Autowired
    TaskMemberService taskMemberService;

    @Test
    void test() throws Exception {

        AnsibleInventory inventory = new AnsibleInventory();
        AnsibleGroup mgrGroup = new AnsibleGroup("mgr");
        inventory.addGroup(mgrGroup);
        mgrGroup.addHost(new AnsibleHost("aliyun"));

        AnsiblePlaybookArgs ansibleArgs = AnsiblePlaybookArgs.builder()
                .playbookName("mgr")
                .inventory(inventory)
                .build();

        TaskMember member = TaskMember.builder().id(2L).taskStatus(TaskStatus.QUEUE.getStatus()).build();
        taskMemberService.addTaskMember(member);

        ansibleExecutorHandler.execute(member, ansibleArgs, (long) (1000 * 60 * 30));
    }

}
