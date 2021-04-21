package com.ansbile;

import com.ansbile.inventory.AnsibleInventoryWriter;
import com.ansbile.inventory.model.AnsibleHost;
import com.ansbile.inventory.model.AnsibleInventory;
import com.ansbile.inventory.model.AnsibleVariable;
import com.ansbile.inventory.model.AnsibleGroup;
import com.google.common.collect.Lists;

import java.io.File;

/**
 * Created by cuikai on 2021/4/20.
 */
public class InventoryWriterTest {
    public static void main(String[] args) throws Exception {
        AnsibleInventory inventory = new AnsibleInventory();

        AnsibleGroup mgrGroup = new AnsibleGroup("mgr");
        inventory.addGroup(mgrGroup);
        mgrGroup.addHost(new AnsibleHost("192.168.1.1", Lists.newArrayList(new AnsibleVariable("isPrimary", "true"))));
        mgrGroup.addHost(new AnsibleHost("192.168.1.2"));
        mgrGroup.addHost(new AnsibleHost("192.168.1.3"));

        AnsibleGroup mysqlGroup = new AnsibleGroup("mysql");
        inventory.addGroup(mysqlGroup);
        mysqlGroup.addHost(new AnsibleHost("192.168.1.1", Lists.newArrayList(new AnsibleVariable("isPrimary", "true"))));
        mysqlGroup.addHost(new AnsibleHost("192.168.1.2"));
        mysqlGroup.addHost(new AnsibleHost("192.168.1.3"));

        AnsibleInventoryWriter.write(inventory, new File("test_hosts"));
    }
}
