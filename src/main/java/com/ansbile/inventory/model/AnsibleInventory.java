package com.ansbile.inventory.model;

import java.util.*;

public class AnsibleInventory {

	private Map<String, AnsibleHost> hosts;

	private Map<String, AnsibleGroup> groups;

	public AnsibleInventory() {
		super();
		this.hosts = new LinkedHashMap<>();
		this.groups = new LinkedHashMap<>();
	}

	public AnsibleInventory(List<AnsibleHost> hosts) {
		this();

		if (hosts != null) {
			for (AnsibleHost h : hosts) {
				this.hosts.put(h.getName(), h);
			}
		}
	}

	public AnsibleInventory(List<AnsibleHost> hosts, List<AnsibleGroup> groups) {
		this(hosts);

		if (groups != null) {
			for (AnsibleGroup g : groups) {
				this.groups.put(g.getName(), g);
			}
		}
	}

	public Collection<AnsibleHost> getHosts() {
		return this.hosts.values();
	}

	public Collection<AnsibleGroup> getGroups() {
		return this.groups.values();
	}

	public void addHost(AnsibleHost host) {
		this.hosts.put(host.getName(), host);
	}

	public void addGroup(AnsibleGroup group) {
		this.groups.put(group.getName(), group);
	}

	public AnsibleHost getHost(String host) {
		return this.hosts.get(host);
	}

	public AnsibleGroup getGroup(String group) {
		return this.groups.get(group);
	}

	public void removeHost(String host) {
		this.hosts.remove(host);
	}

	public void removeGroup(String group) {
		this.groups.remove(group);
	}

	public void clear() {
		this.hosts.clear();
		this.groups.clear();
	}
}
