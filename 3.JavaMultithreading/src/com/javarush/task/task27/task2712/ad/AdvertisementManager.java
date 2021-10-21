package com.javarush.task.task27.task2712.ad;

import java.util.List;

public class AdvertisementManager {
    private final AdvertisementStorage storage = AdvertisementStorage.getInstance();
    private int timeSeconds;

    public AdvertisementManager(int timeSeconds) {
        this.timeSeconds = timeSeconds;
    }

    public void processVideos() throws NoVideoAvailableException{
        List<Advertisement> advertisementList = storage.list();
        if (advertisementList.size() == 0) {
            throw new NoVideoAvailableException();
        }
        //Сортировка происходит в порядке уменьшения стоимости показа одного рекламного ролика в копейках
        //Вторичная сортировка - по увеличению стоимости показа одной секунды рекламного ролика в тысячных частях копейки
        advertisementList.sort((o1, o2) -> {
            if (o1.getAmountPerOneDisplaying() != o2.getAmountPerOneDisplaying())
                return Long.compare(o1.getAmountPerOneDisplaying(), o2.getAmountPerOneDisplaying());
            else
                return Long.compare(o1.getAmountPerOneDisplaying() / o1.getDuration(), o2.getAmountPerOneDisplaying() / o2.getDuration());
        });
        //Показ роликов
        advertisementList.forEach(Advertisement::revalidate);
    }
}
