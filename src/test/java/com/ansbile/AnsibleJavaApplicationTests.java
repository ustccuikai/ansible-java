package com.ansbile;

import com.ansbile.config.AnsibleConfig;
import com.ansbile.config.AnsiblePlaybookArgsBuilder;
import com.ansbile.exec.AnsibleExecutorHandler;
import com.ansbile.inventory.model.AnsibleHost;
import com.ansbile.inventory.model.AnsibleInventory;
import com.ansbile.config.AnsiblePlaybookArgs;
import com.ansbile.inventory.model.AnsibleGroup;
import org.apache.commons.exec.CommandLine;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AnsibleJavaApplicationTests {

    @Autowired
    AnsibleConfig ansibleConfig;

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
        AnsibleExecutorHandler.execute(commandLine, (long) (1000 * 60 * 30));
    }

}
