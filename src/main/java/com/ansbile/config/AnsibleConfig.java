package com.ansbile.config;

import com.google.common.base.Joiner;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "ansible", ignoreInvalidFields = true)
public class AnsibleConfig {

    public final static String ANSIBLE_HOSTS = "ansible_hosts";

    public final static String ANSIBLE_PLAYBOOK = "playbook";

    private String bin;
    private String version;
    private String playbookBin;
    private String logPath;
    // 主机配置文件目录
    private String dataPath;

    public String acqInventoryPath() {
        return Joiner.on("/").join(dataPath, "inventory");
    }

    public String acqScriptPath() {
        return Joiner.on("/").join(dataPath, "script");
    }

    public String acqPlaybookPath() {
        return Joiner.on("/").join(dataPath, ANSIBLE_PLAYBOOK);
    }

    public String acqPrivateKey() {
        return Joiner.on("/").join(dataPath, "private_key/id_rsa");
    }


    /**
     * playbook任务日志目录
     * @param taskId
     * @return /log/ansible/playbook/${taskId}
     */
    public String getPlaybookLogPath(int taskId) {
        return Joiner.on("/").join(logPath, ANSIBLE_PLAYBOOK, taskId);
    }
}
