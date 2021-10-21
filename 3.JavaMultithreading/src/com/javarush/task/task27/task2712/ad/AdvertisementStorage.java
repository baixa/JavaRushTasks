package com.javarush.task.task27.task2712.ad;

import java.util.ArrayList;
import java.util.List;

public class AdvertisementStorage {
    private static final AdvertisementStorage storage = new AdvertisementStorage();
    private final List<Advertisement> videos = new ArrayList<>();

    private AdvertisementStorage() {
        for (int i = 1; i <= 20; i++) {
            Object someContent = new Object();
            Advertisement advertisement = new Advertisement(someContent, "Video #" + i, i * 15898, i * 128, i*60);
            add(advertisement);
        }
    }

    public List<Advertisement> list() {
        return videos;
    }

    public void add(Advertisement advertisement) {
        videos.add(advertisement);
    }

    public static AdvertisementStorage getInstance() {
        return storage;
    }
}
