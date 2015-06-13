package com.supersmashcoders.backtobackhackathon.models;

import android.util.Log;

import com.supersmashcoders.backtobackhackathon.converters.Converters;
import com.supersmashcoders.backtobackhackathon.converters.DateConverter;
import com.supersmashcoders.backtobackhackathon.converters.JsonArrayConverter;
import com.supersmashcoders.backtobackhackathon.enums.Tag;
import com.supersmashcoders.backtobackhackathon.util.JSONUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EventModel implements Serializable {
    private Long id;
    private String name;
    private String description;
    private Date startDate;
    private Date endDate;
    private UserEntity owner;
    private Tag tag;
    private List<UserEntity> attendants;

    private EventModel(Long id, String name, String description, Date startDate, Date endDate,
                       UserEntity owner, Tag tag, List<UserEntity> attendants) {
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
                                UserEntity owner, Tag tag, List<UserEntity> attendants) {
        return new EventModel(id, name, description, startDate, endDate, owner, tag, attendants);
    }

    public static EventModel of(String name, String description, Date startDate, Date endDate,
                                Tag tag) {
        return new EventModel(0L, name, description, startDate, endDate, null, tag, new ArrayList<UserEntity>());
    }

    public static EventModel of(JSONObject jsonEvent) {
        try {
            Long id = jsonEvent.getLong("id");
            String name = jsonEvent.getString("name");
            String description = jsonEvent.getString("description");
            Date startDate = DateConverter.toDate(jsonEvent.getString("startDate"), DateConverter.DateFormat.DATE_FORMAT);
            Date endDate = DateConverter.toDate(jsonEvent.getString("endDate"), DateConverter.DateFormat.DATE_FORMAT);
            UserEntity owner = UserEntity.of(jsonEvent.getJSONObject("owner"));
            Tag tag = Tag.fromId(jsonEvent.getString("tag"));
            List<UserEntity> attendants = JsonArrayConverter.toList(JSONUtil.getArrayOrEmpty(jsonEvent, "attendants"), new Converters.UserConverter());
            return new EventModel(id, name, description, startDate, endDate, owner, tag, attendants);
        } catch(JSONException e) {
            Log.e("JSON PARSE", "ERROR PARSING " + jsonEvent.toString());
            return null;
        }
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

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }

    public List<UserEntity> getAttendants() {
        return attendants;
    }

    public void setAttendants(List<UserEntity> attendants) {
        this.attendants = attendants;
    }
}
