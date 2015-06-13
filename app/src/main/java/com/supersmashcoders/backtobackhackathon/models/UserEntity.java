package com.supersmashcoders.backtobackhackathon.models;

import java.io.Serializable;

public class UserEntity implements Serializable {

    private Long id;
    private String username;

    private UserEntity(Long id, String username) {
        this.id = id;
        this.username = username;
    }

    public static UserEntity of(Long id, String username) {
        return new UserEntity(id, username);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
