package com.ansbile.model;

import java.util.*;

public class AnsibleGroup {

	private String name;

	private Map<String, AnsibleHost> hosts;

	private Map<String, AnsibleGroup> subgroups;

	private Map<String, AnsibleVariable> variables;

	public AnsibleGroup(String name) {
		super();
		this.name = name;
		this.hosts = new LinkedHashMap<>();
		this.subgroups = new LinkedHashMap<>();
		this.variables = new LinkedHashMap<>();
	}

	public AnsibleGroup(String name, List<AnsibleHost> hosts) {
		this(name);

		if (hosts != null) {
			for (AnsibleHost h : hosts) {
				this.hosts.put(h.getName(), h);
			}
		}
	}

	public AnsibleGroup(String name, List<AnsibleHost> hosts, List<AnsibleVariable> variables) {
		this(name, hosts);

		if (variables != null) {
			for (AnsibleVariable v : variables) {
				this.variables.put(v.getName(), v);
			}
		}
	}

	public String getName() {
		return name;
	}

	public Collection<AnsibleHost> getHosts() {
		return this.hosts.values();
	}

	public Collection<AnsibleGroup> getSubgroups() {
		return this.subgroups.values();
	}

	public Collection<AnsibleVariable> getVariables() {
		return this.variables.values();
	}

	public void addHost(AnsibleHost host) {
		this.hosts.put(host.getName(), host);
	}

	public void addHosts(List<AnsibleHost> hosts) {
		for (AnsibleHost h : hosts) {
			addHost(h);
		}
	}

	public void addSubgroup(AnsibleGroup subgroup) {
		this.subgroups.put(subgroup.getName(), subgroup);
	}

	public void addVariable(AnsibleVariable variable) {
		this.variables.put(variable.getName(), variable);
	}

	public void addVariables(List<AnsibleVariable> variables) {
		for (AnsibleVariable v : variables) {
			addVariable(v);
		}
	}

	public AnsibleHost getHost(String host) {
		return this.hosts.get(host);
	}

	public AnsibleVariable getVariable(String variable) {
		return this.variables.get(variable);
	}

	public void removeHost(String host) {
		this.hosts.remove(host);
	}

	public void removeSubgroup(String subgroup) {
		this.subgroups.remove(subgroup);
	}

	public void removeVariable(String variable) {
		this.variables.remove(variable);
	}

	public void clear() {
		this.hosts.clear();
		this.subgroups.clear();
		this.variables.clear();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if ((o == null) || (getClass() != o.getClass()))
			return false;

		AnsibleGroup group = (AnsibleGroup) o;

		return name.equals(group.name);
	}

	@Override
	public int hashCode() {
		return name.hashCode();
	}
}
