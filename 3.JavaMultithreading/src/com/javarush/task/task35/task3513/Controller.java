package com.javarush.task.task35.task3513;

import java.awt.event.KeyAdapter;

/**
 * Следит за нажатием клавиш во время игры
 */
public class Controller extends KeyAdapter {
    private Model model;

    public Controller(Model model) {
        this.model = model;
    }

    public Tile[][] getGameTiles() {
        return model.getGameTiles();
    }

    public int getScore() {
        return model.score;
    }
}
