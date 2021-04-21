package com.ansbile.inventory;

import com.ansbile.inventory.model.AnsibleHost;
import com.ansbile.inventory.model.AnsibleInventory;
import com.ansbile.inventory.model.AnsibleVariable;
import com.ansbile.inventory.model.AnsibleGroup;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.StringTokenizer;
import java.util.stream.Stream;

public class AnsibleInventoryReader {

	public static AnsibleInventory read(String text) {
		final AnsibleInventory inventory = new AnsibleInventory();

		final StringTokenizer tokenizer = new StringTokenizer(text, " \t\n\r\f", true);

		AnsibleGroup group = null;
		AnsibleHost host = null;
		boolean skipComment = false;
		boolean isVarsBlock = false;
		boolean isChildrenBlock = false;
		while (tokenizer.hasMoreTokens()) {
			final String token = tokenizer.nextToken();

			// New line, reset the comment flag
			if ("\n".equals(token)) {
				skipComment = false;
				continue;
			}

			// We are still reading a comment line
			if (skipComment) {
				continue;
			}

			// Ignore separators
			if (" ".equals(token) || "\t".equals(token) || "\r".equals(token) || "\f".equals(token)) {
				continue;
			}

			// We are reading a comment
			if (token.startsWith(";") || token.startsWith("#")) {
				skipComment = true;
				continue;
			}

			if (token.startsWith("[")) {
				host = null;
				isChildrenBlock = false;
				isVarsBlock = false;

				String groupName = token.replaceAll("^\\[", "").replaceAll("]$", "");

				if (groupName.contains(":")) {
					final String[] g = groupName.split(":");

					groupName = g[0];

					if ("vars".equals(g[1])) {
						isVarsBlock = true;
						group = inventory.getGroup(groupName);
					} else if ("children".equals(g[1])) {
						isChildrenBlock = true;
						group = new AnsibleGroup(groupName);
						inventory.addGroup(group);
					}
				} else {
					group = new AnsibleGroup(groupName);
					inventory.addGroup(group);
				}
			} else if (token.contains("=")) {
				final String[] v = token.split("=");
				// Replace YAML backslashes escapes
				final AnsibleVariable variable = new AnsibleVariable(v[0], v[1].replace("\\\\", "\\"));

				if (host != null) {
					host.addVariable(variable);
				} else if (isVarsBlock && group != null) {
					for (AnsibleGroup s : group.getSubgroups()) {
						for (AnsibleHost h : s.getHosts()) {
							h.addVariable(variable);
						}
					}
					for (AnsibleHost h : group.getHosts()) {
						h.addVariable(variable);
					}
				}
			} else {
				if (group == null) {
					host = new AnsibleHost(token);
					inventory.addHost(host);
				} else if (isChildrenBlock) {
					final AnsibleGroup g = inventory.getGroup(token);
					if (g != null) {
						group.addSubgroup(g);
					} else {
						group.addSubgroup(new AnsibleGroup(token));
					}
				} else {
					host = new AnsibleHost(token);
					group.addHost(host);
				}
			}
		}

		return inventory;
	}

	public static AnsibleInventory read(final Path inventoryPath) throws IOException {
		StringBuilder contentBuilder = new StringBuilder();
		Stream<String> stream = Files.lines(inventoryPath, StandardCharsets.UTF_8);
		stream.forEach(s -> contentBuilder.append(s).append("\n"));

		return read(contentBuilder.toString());
	}
}
