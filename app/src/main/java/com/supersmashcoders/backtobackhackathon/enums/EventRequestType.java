package com.supersmashcoders.backtobackhackathon.enums;


public enum EventRequestType {
    MY_SUBSCRIBED_EVENTS ("Subscribed", "My Subscribed"),
    MY_CREATED_EVENTS ("Created", "My Created"),
    ALL_EVENTS ("All", "All Events");

    private String requestName;
    private String displayName;

    EventRequestType(String requestName, String displayName) {
        this.requestName = requestName;
        this.displayName = displayName;
    }

    public String getRequestName() {
        return requestName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static String[] displayNames() {
        EventRequestType[] tags = values();
        String[] names = new String[tags.length];

        for (int i = 0; i < tags.length; i++) {
            names[i] = tags[i].getDisplayName();
        }

        return names;
    }

    public static EventRequestType fromDisplayName(String name) {
        EventRequestType tag = null;
        for (EventRequestType currentTag: values()) {
            if (currentTag.getDisplayName().equals(name)) {
                return currentTag;
            }
        }
        throw new IllegalArgumentException("Invalid tag name provided: " + name);
    }
}
