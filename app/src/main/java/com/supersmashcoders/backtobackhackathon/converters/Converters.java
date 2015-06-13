package com.supersmashcoders.backtobackhackathon.converters;


import com.supersmashcoders.backtobackhackathon.models.UserEntity;

import org.json.JSONObject;

public class Converters {
    public static class UserConverter implements Converter<UserEntity> {
        @Override
        public UserEntity convert(JSONObject object) {
            return UserEntity.of(object);
        }
    }
}
