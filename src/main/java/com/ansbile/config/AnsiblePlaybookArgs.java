package com.ansbile.config;

import com.ansbile.model.AnsibleInventory;
import com.google.common.collect.Maps;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class AnsiblePlaybookArgs {
    private String playbookName;

    /**
     * hosts 主机ip
     */
    private String hosts;

    /**
     *  --private-key PRIVATE_KEY_FILE, --key-file PRIVATE_KEY_FILE
     *                         use this file to authenticate the connection
     */
    private String keyFile;

    /**
     *  -i INVENTORY, --inventory INVENTORY, --inventory-file INVENTORY
     *                         specify inventory host path or comma separated host
     *                         list. --inventory-file is deprecated
     */
    private String inventoryFile;

    private AnsibleInventory inventory;

    /**
     * -t TAGS, --tags TAGS  only run plays and tasks tagged with these values
     */
    private String tags;


    @Builder.Default
    private Integer forks = 1;

    /**
     *   --become-user BECOME_USER
     *                         run operations as this user (default=root)
     */
    @Builder.Default
    private String becomeUser = "root";

    /**
     * -K,--ask-become-pass, ask for privilege escalation password
     */
    private String becomePassword;

    /**
     * -u REMOTE_USER, --user REMOTE_USER
     *                         connect as this user (default=None)
     */
    @Builder.Default
    private String remoteUser;

    /**
     * -e EXTRA_VARS, --extra-vars EXTRA_VARS
     *                         set additional variables as key=value or YAML/JSON, if filename prepend with @
     */
    @Builder.Default
    private Map<String, String> extraVars = Maps.newHashMap();

    /**
     *   -v, --verbose         verbose mode (-vvv for more, -vvvv to enable
     *                         connection debugging)
     */
    private String verbose;
}
