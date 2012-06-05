package me.sirsavary.townmanager.objects;

import java.util.Collection;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum HousingType {
	OPEN("Open"),
	CLOSED("Closed"),
	INVITE_ONLY("Invite Only");

	private String name;

	private static final Map<String, HousingType> mapping = new HashMap<String, HousingType>();

	static {
		for (HousingType type : EnumSet.allOf(HousingType.class)) {
			mapping.put(type.name, type);
		}
	}

	private HousingType(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public static HousingType fromName(String name) {
		return mapping.get(name);
	}

	public static Collection<HousingType> getSkills() {
		return mapping.values();
	}
}
