package com.ansbile.ansible.inventory;

import com.ansbile.ansible.model.AnsibleHost;
import com.ansbile.ansible.model.AnsibleGroup;
import com.ansbile.ansible.model.AnsibleInventory;
import com.ansbile.ansible.model.AnsibleVariable;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class AnsibleInventoryWriter {
	private static String groupHeader(String group) {
		return "[" + group + "]\n";
	}

	private static String variableBlock(AnsibleVariable variable) {
		final String val = variable.getValue().toString();

		// Escape backslashes for YAML
		return variable.getName() + "=" + val.replace("\\", "\\\\");
	}

	private static String groupVarsHeader(String group) {
		return "[" + group + ":vars]\n";
	}

	private static String groupOfGroupHeader(String group) {
		return "[" + group + ":children]\n";
	}

	private static String printHost(AnsibleHost host) {
		final StringBuilder builder = new StringBuilder();
		builder.append(host.getName());

		for (AnsibleVariable variable : host.getVariables()) {
			builder.append(" " + variableBlock(variable));
		}

		builder.append("\n");

		return builder.toString();
	}

	private static void printHost(AnsibleHost host, OutputStream stream) throws IOException {
		stream.write(host.getName().getBytes());

		for (AnsibleVariable variable : host.getVariables()) {
			stream.write((" " + variableBlock(variable)).getBytes());
		}

		stream.write("\n".getBytes());
	}

	public static String write(AnsibleInventory inventory) {
		final StringBuilder builder = new StringBuilder();

		for (AnsibleHost host : inventory.getHosts()) {
			builder.append(printHost(host));
		}

		for (AnsibleGroup group : inventory.getGroups()) {
			if (!group.getSubgroups().isEmpty()) {
				builder.append(groupOfGroupHeader(group.getName()));

				if (!group.getSubgroups().isEmpty()) {
					for (AnsibleGroup g : group.getSubgroups()) {
						builder.append(g.getName() + "\n");
					}
				} else {
					builder.append("\n");
				}
			}

			if (!group.getHosts().isEmpty()) {
				builder.append(groupHeader(group.getName()));

				for (AnsibleHost host : group.getHosts()) {
					builder.append(printHost(host));
				}
			}

			if (!group.getVariables().isEmpty()) {
				builder.append(groupVarsHeader(group.getName()));

				if (!group.getVariables().isEmpty()) {
					for (AnsibleVariable variable : group.getVariables()) {
						builder.append(variableBlock(variable) + "\n");
					}
				} else {
					builder.append("\n");
				}
			}

			builder.append("\n");
		}

		return builder.toString();
	}

	public static void write(AnsibleInventory inventory, File file) throws IOException {
		FileOutputStream stream = new FileOutputStream(file);
		for (AnsibleHost host : inventory.getHosts()) {
			printHost(host, stream);
		}

		for (AnsibleGroup group : inventory.getGroups()) {
			if (!group.getSubgroups().isEmpty()) {
				stream.write(groupOfGroupHeader(group.getName()).getBytes());

				if (!group.getSubgroups().isEmpty()) {
					for (AnsibleGroup g : group.getSubgroups()) {
						stream.write((g.getName() + "\n").getBytes());
					}
				} else {
					stream.write("\n".getBytes());
				}
			}

			if (!group.getHosts().isEmpty()) {
				stream.write(groupHeader(group.getName()).getBytes());

				for (AnsibleHost host : group.getHosts()) {
					printHost(host, stream);
				}
			}

			if (!group.getVariables().isEmpty()) {
				stream.write("\n".getBytes());
				stream.write(groupVarsHeader(group.getName()).getBytes());

				if (!group.getVariables().isEmpty()) {
					for (AnsibleVariable variable : group.getVariables()) {
						stream.write((variableBlock(variable) + "\n").getBytes());
					}
				} else {
					stream.write("\n".getBytes());
				}
			}

			stream.write("\n".getBytes());
		}
	}

}
