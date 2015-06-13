package com.supersmashcoders.backtobackhackathon.models;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class EventModel implements Serializable {
    private Long id;
    private String name;
    private String description;
    private Date startDate;
    private Date endDate;
    private UserEntity owner;
    private String tag;
    private List<UserEntity> attendants;

    private EventModel(Long id, String name, String description, Date startDate, Date endDate,
                       UserEntity owner, String tag, List<UserEntity> attendants) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.owner = owner;
        this.tag = tag;
        this.attendants = attendants;
    }

    public static EventModel of(Long id, String name, String description, Date startDate, Date endDate,
                                UserEntity owner, String tag, List<UserEntity> attendants) {
        return new EventModel(id, name, description, startDate, endDate, owner, tag, attendants);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public UserEntity getOwner() {
        return owner;
    }

    public void setOwner(UserEntity owner) {
        this.owner = owner;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public List<UserEntity> getAttendants() {
        return attendants;
    }

    public void setAttendants(List<UserEntity> attendants) {
        this.attendants = attendants;
    }
}
