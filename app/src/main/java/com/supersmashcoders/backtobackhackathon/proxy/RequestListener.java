package com.supersmashcoders.backtobackhackathon.proxy;

public interface RequestListener<T> {
    void onComplete(T object);
    void onError();
}
