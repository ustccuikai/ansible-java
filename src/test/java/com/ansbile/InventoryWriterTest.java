package com.ansbile;

/**
 * Created by cuikai on 2021/4/20.
 */
public class InventoryWriterTest {
    public static void main(String[] args) throws Exception {
//        AnsibleInventory inventory = new AnsibleInventory();
//
//        AnsibleGroup mgrGroup = new AnsibleGroup("mgr");
//        inventory.addGroup(mgrGroup);
//        mgrGroup.addHost(new AnsibleHost("192.168.1.1", Lists.newArrayList(new AnsibleVariable("isPrimary", "true"))));
//        mgrGroup.addHost(new AnsibleHost("192.168.1.2"));
//        mgrGroup.addHost(new AnsibleHost("192.168.1.3"));
//
//        AnsibleGroup mysqlGroup = new AnsibleGroup("mysql");
//        inventory.addGroup(mysqlGroup);
//        mysqlGroup.addHost(new AnsibleHost("192.168.1.1", Lists.newArrayList(new AnsibleVariable("isPrimary", "true"))));
//        mysqlGroup.addHost(new AnsibleHost("192.168.1.2"));
//        mysqlGroup.addHost(new AnsibleHost("192.168.1.3"));
//
//        String inventoryJson = new GsonBuilder().create().toJson(inventory);
//        System.out.println(inventoryJson);
//
//        String json2 = "{\"groups\":{\"mgr\":{\"name\":\"mgr\",\"hosts\":{\"192.168.1.1\":{\"name\":\"192.168.1.1\",\"variables\":[{\"name\":\"isPrimary\",\"value\":\"true\"}]},\"192.168.1.2\":{\"name\":\"192.168.1.2\",\"variables\":[]},\"192.168.1.3\":{\"name\":\"192.168.1.3\",\"variables\":[]}},\"subgroups\":{},\"variables\":{}},\"mysql\":{\"name\":\"mysql\",\"hosts\":{\"192.168.1.1\":{\"name\":\"192.168.1.1\",\"variables\":[{\"name\":\"isPrimary\",\"value\":\"true\"}]},\"192.168.1.2\":{\"name\":\"192.168.1.2\",\"variables\":[]},\"192.168.1.3\":{\"name\":\"192.168.1.3\",\"variables\":[]}},\"subgroups\":{},\"variables\":{}}}}";
//        AnsibleInventory ansibleInventory = new GsonBuilder().create().fromJson(json2, AnsibleInventory.class);
//        String ansibleInventory2 = new GsonBuilder().create().toJson(ansibleInventory);
//        System.out.println(ansibleInventory2);
//
//        AnsibleInventoryWriter.write(ansibleInventory, new File("test_hosts"));
    }
}
