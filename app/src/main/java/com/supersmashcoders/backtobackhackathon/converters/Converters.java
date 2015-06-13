package com.supersmashcoders.backtobackhackathon.converters;


import com.supersmashcoders.backtobackhackathon.models.ProductModel;
import com.supersmashcoders.backtobackhackathon.models.UserModel;

import org.json.JSONObject;

public class Converters {
    public static class UserConverter implements Converter<UserModel> {
        @Override
        public UserModel convert(JSONObject object) {
            return UserModel.of(object);
        }
    }

    public static class ProductConverter implements Converter<ProductModel> {
        @Override
        public ProductModel convert(JSONObject object) {
            return ProductModel.of(object);
        }
    }
}
