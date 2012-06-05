package me.sirsavary.townmanager.objects;

import java.util.Collection;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum ResourceListType {
	WHITE_LIST("White List"),
	BLACK_LIST("Black List");

	private String name;

    private static final Map<String, ResourceListType> mapping = new HashMap<String, ResourceListType>();

    static {
        for (ResourceListType type : EnumSet.allOf(ResourceListType.class)) {
            mapping.put(type.name, type);
        }
    }

    private ResourceListType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static ResourceListType fromName(String name) {
        return mapping.get(name);
    }
    
    public static Collection<ResourceListType> getSkills() {
    	return mapping.values();
    }
}
