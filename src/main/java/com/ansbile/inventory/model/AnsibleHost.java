package com.ansbile.inventory.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AnsibleHost {

	private String name;

	private Set<AnsibleVariable> variables;

	public AnsibleHost(String name) {
		super();
		this.name = name;
		this.variables = new HashSet<>();
	}

	public AnsibleHost(String name, List<AnsibleVariable> variables) {
		this(name);

		if (variables != null) {
			for (AnsibleVariable v : variables) {
				this.variables.add(v);
			}
		}
	}

	public String getName() {
		return name;
	}

	public Set<AnsibleVariable> getVariables() {
		return this.variables;
	}

	public void addVariable(AnsibleVariable variable) {
		this.variables.add(variable);
	}

	public void addVariables(List<AnsibleVariable> variables) {
		for (AnsibleVariable v : variables) {
			addVariable(v);
		}
	}

	public AnsibleVariable getVariable(String variableName) {
		for (AnsibleVariable v : variables) {
			if (v.getName().equals(variableName)) {
				return v;
			}
		}

		return null;
	}

	public void removeVariable(String variableName) {
		this.variables.remove(variableName);
	}

	public void clear() {
		this.variables.clear();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if ((o == null) || (getClass() != o.getClass()))
			return false;

		AnsibleHost host = (AnsibleHost) o;

		return name.equals(host.name);
	}

	@Override
	public int hashCode() {
		return name.hashCode();
	}
}
