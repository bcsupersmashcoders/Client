package com.supersmashcoders.backtobackhackathon.enums;

public enum Tag {
    HIKE_CAMP ("Hike and Camping", "bcsCat7000003"),
    BIKE ("Bike", "bcsCat140000003"),
    RUN ("Run", "bcsCat15000003"),
    CLIMB ("Climb", "bcsCat8000003"),
    FLY_FISH ("Fly Fish", "bcsCat12000003"),
    PADDLE("Paddle", "bcsCat9000003"),
    SURF ("Surf", "cat100205204"),
    SKI ("Ski", "bcsCat5000003"),
    SNOWBOARD ("Snowboard", "bcsCat6000003"),
    SNOWSHOE ("Snowshoe", "bcsCat11000003"),
    TRAVEL ("Travel Gear", "bcsCat10000003");

    String displayName;
    String id;

    Tag(String displayName, String id) {
        this.displayName = displayName;
        this.id = id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getId() {
        return id;
    }

    public static String[] displayNames() {
        Tag[] tags = values();
        String[] names = new String[tags.length];

        for (int i = 0; i < tags.length; i++) {
            names[i] = tags[i].getDisplayName();
        }

        return names;
    }
}
