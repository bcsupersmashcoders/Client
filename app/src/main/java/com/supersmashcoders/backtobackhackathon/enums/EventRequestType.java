package com.supersmashcoders.backtobackhackathon.enums;


public enum EventRequestType {
    MY_SUBSCRIBED_EVENTS ("Subscribed"),
    MY_CREATED_EVENTS ("Created"),
    ALL_EVENTS ("All");

    private String requestName;

    EventRequestType(String requestName) {
        this.requestName = requestName;
    }

    public String getRequestName() {
        return requestName;
    }
}
