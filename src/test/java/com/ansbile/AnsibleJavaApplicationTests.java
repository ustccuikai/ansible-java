package com.ansbile;

import com.ansbile.exec.AnsibleExecutorHandler;
import com.ansbile.model.*;
import com.ansbile.config.AnsiblePlaybookArgs;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AnsibleJavaApplicationTests {

    @Autowired
    AnsibleExecutorHandler ansibleExecutorHandler;

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

        TaskMember member = TaskMember.builder().id(1).taskStatus(TaskStatus.QUEUE.getStatus()).build();

        ansibleExecutorHandler.execute(member, ansibleArgs, (long) (1000 * 60 * 30));
    }

}
