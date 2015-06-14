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
    private String owner;
    private Tag tag;
    private List<String> attendants;
    private List<ProductModel> products;

    private EventModel(Long id, String name, String description, Date startDate, Date endDate,
                       String owner, Tag tag, List<String> attendants, List<ProductModel> products) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.owner = owner;
        this.tag = tag;
        this.attendants = attendants;
        this.products = products;
    }

    public static EventModel of(Long id, String name, String description, Date startDate, Date endDate,
                                String owner, Tag tag, List<String> attendants, List<ProductModel> products) {
        return new EventModel(id, name, description, startDate, endDate, owner, tag, attendants, products);
    }

    public static EventModel of(String name, String description, Date startDate, Date endDate,
                                Tag tag) {
        return new EventModel(0L, name, description, startDate, endDate, null, tag, new ArrayList<String>(), new ArrayList<ProductModel>());
    }

    public static EventModel of(JSONObject jsonEvent) {
        try {
            Long id = jsonEvent.getLong("id");
            String name = jsonEvent.getString("name");
            String description = jsonEvent.getString("description");
            Date startDate = DateConverter.toDate(jsonEvent.getString("startDate"), DateConverter.DateFormat.DATE_FORMAT);
            Date endDate = DateConverter.toDate(jsonEvent.getString("endDate"), DateConverter.DateFormat.DATE_FORMAT);
            String owner = jsonEvent.getString("owner");
            Tag tag = Tag.fromId(jsonEvent.getString("tag"));
            List<String> attendants = JsonArrayConverter.toStringList(JSONUtil.getArrayOrEmpty(jsonEvent, "attendants"));
            List<ProductModel> products = JsonArrayConverter.toList(JSONUtil.getArrayOrEmpty(jsonEvent, "products"), new Converters.ProductConverter());
            return new EventModel(id, name, description, startDate, endDate, owner, tag, attendants, products);
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

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }

    public List<String> getAttendants() {
        return attendants;
    }

    public void setAttendants(List<String> attendants) {
        this.attendants = attendants;
    }

    public List<ProductModel> getProducts() {
        return products;
    }

    public void setProducts(List<ProductModel> products) {
        this.products = products;
    }

    /*
        {
      "name": "Card games on Battle City",
      "endDate": "2015-04-15T20:20:50.520Z",
      "description": "Playing card games on a city",
      "startDate": "2015-04-12T20:20:50.520Z",
      "owner":
      {
        "username": "username"
      },
      "tag": "bcsCat7000003"
    }
         */
    public JSONObject asJSON() {
        JSONObject object = new JSONObject();
        try {
            object.put("name", getName());
            object.put("description", getDescription());
            object.put("startDate", DateConverter.toString(getStartDate(), DateConverter.DateFormat.ISO_FORMAT));
            object.put("endDate", DateConverter.toString(getEndDate(), DateConverter.DateFormat.ISO_FORMAT));
            object.put("owner", getOwner());
            object.put("tag", getTag().getId());
        } catch (JSONException e) {
            Log.e("JSON PARSE", "Error converting object to JSON");
        }
        return object;
    }
}
