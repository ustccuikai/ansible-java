package com.ansbile.config;

import com.ansbile.inventory.AnsibleInventoryWriter;
import com.google.common.base.Joiner;
import com.google.common.collect.Maps;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class AnsiblePlaybookArgsBuilder {
    public final static String ANSIBLE_PLAYBOOK = "ansible-playbook";

    public static CommandLine build(AnsiblePlaybookArgs args) throws Exception {

        CommandLine commandLine = buildCommandLine(ANSIBLE_PLAYBOOK, args);

        // 外部变量
        Map<String, String> extraVarsMap = Maps.newHashMap();
        if (args.getExtraVars() != null)
            extraVarsMap = args.getExtraVars();

        if (StringUtils.isNotBlank(args.getHosts())) {
            extraVarsMap.put("hosts", args.getHosts());
        }

        if (StringUtils.isNotBlank(args.getBecomePassword())) {
            extraVarsMap.put("ansible_become_password", args.getBecomePassword());
        }

        if (!extraVarsMap.isEmpty()) {
            commandLine.addArgument("-e");
            commandLine.addArgument(convertExtraVars(extraVarsMap), false);
        }

        // 标签执行 -t task1,task2
        if (args.getTags() != null && !args.getTags().isEmpty()) {
            commandLine.addArgument("-t");
            commandLine.addArgument(args.getTags());
        }

        // playbook脚本
        if (StringUtils.isNotBlank(args.getPlaybookName())) {
            File ansibleFile = ResourceUtils.getFile("classpath:ansible");
            System.out.println(ansibleFile.getAbsolutePath() + "/" + args.getPlaybookName() + ".yml");
            commandLine.addArgument(ansibleFile.getAbsolutePath() + "/" + args.getPlaybookName() + ".yml");
        }

        return commandLine;
    }

    private static CommandLine buildCommandLine(String command, AnsiblePlaybookArgs args) throws IOException {
        CommandLine commandLine = new CommandLine(command);

        // use this file to authenticate the connection
        if (StringUtils.isNotBlank(args.getKeyFile())) {
            commandLine.addArgument("--key-file");
            commandLine.addArgument(args.getKeyFile());
        }

        // 指定主机文件，如果不指定则用默认主机文件
        commandLine.addArgument("-i");
        if (args.getInventory() != null) {
            String path = ResourceUtils.getURL("classpath:").getPath();
            File inventoryPath = new File(path, "inventory");
            if (!inventoryPath.exists()) {
                inventoryPath.mkdir();
            }
            File inventoryFile = new File(inventoryPath, args.getPlaybookName() + "_" + System.currentTimeMillis());
            System.out.println(inventoryFile.getAbsolutePath());
            AnsibleInventoryWriter.write(args.getInventory(), inventoryFile);
            commandLine.addArgument(inventoryFile.getAbsolutePath());
        } else if (StringUtils.isNotBlank(args.getInventoryFile())) {
            commandLine.addArgument(args.getInventoryFile());
        }

        // remote-user
        if (StringUtils.isNotBlank(args.getRemoteUser())) {
            commandLine.addArgument("--user");
            commandLine.addArgument(args.getRemoteUser());
        }

        commandLine.addArgument("--become");
        commandLine.addArgument("--become-method=sudo");
        commandLine.addArgument("--become-user");
        if (StringUtils.isNotBlank(args.getBecomeUser())) {
            commandLine.addArgument(args.getBecomeUser());
        } else {
            commandLine.addArgument("root");
        }

        // 并行任务数
        if (args.getForks() != null && args.getForks() != 5) {
            commandLine.addArgument("-f");
            commandLine.addArgument(args.getForks().toString());
        }

        return commandLine;
    }


    private static String convertExtraVars(Map<String, String> extraVarsMap) {
        String extraVars = null;
        for (String key : extraVarsMap.keySet())
            extraVars = Joiner.on(" ").skipNulls().join(extraVars, Joiner.on("=").join(key, extraVarsMap.get(key)));
        return extraVars;
    }
}
