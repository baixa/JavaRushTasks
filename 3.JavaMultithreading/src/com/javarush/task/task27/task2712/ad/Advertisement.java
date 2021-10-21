package com.javarush.task.task27.task2712.ad;

public class Advertisement {
    private Object content;//видео
    private String name;//Название
    private long initialAmount;//Стоимость рекламы в копейках
    private int hits;//количество оплаченных показов
    private int duration;//продолжительность в секундах
    private long amountPerOneDisplaying;//стоимость одного показа рекламного объявления


    public Advertisement(Object content, String name, long initialAmount, int hits, int duration) {
        this.content = content;
        this.name = name;
        this.initialAmount = initialAmount;
        this.hits = hits;
        this.duration = duration;
        if (hits == 0)
            amountPerOneDisplaying = 0;
        else
            amountPerOneDisplaying = initialAmount/hits;
    }

    public long getAmountPerOneDisplaying() {
        return amountPerOneDisplaying;
    }

    public int getDuration() {
        return duration;
    }

    public String getName() {
        return name;
    }

    public void revalidate() {
        if (hits < 1)
            throw new UnsupportedOperationException();
        else
            hits--;
    }
}
