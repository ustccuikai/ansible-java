package com.ansbile;

import com.ansbile.config.AnsibleConfig;
import com.ansbile.config.AnsiblePlaybookArgsBuilder;
import com.ansbile.exec.AnsibleExecutorHandler;
import com.ansbile.model.AnsibleHost;
import com.ansbile.model.AnsibleInventory;
import com.ansbile.config.AnsiblePlaybookArgs;
import com.ansbile.model.AnsibleGroup;
import org.apache.commons.exec.CommandLine;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AnsibleJavaApplicationTests {

    @Autowired
    AnsibleConfig ansibleConfig;

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

        CommandLine commandLine = AnsiblePlaybookArgsBuilder.build(ansibleConfig, ansibleArgs);
        System.out.println(commandLine);
        ansibleExecutorHandler.execute(null, commandLine, (long) (1000 * 60 * 30));
    }

}
