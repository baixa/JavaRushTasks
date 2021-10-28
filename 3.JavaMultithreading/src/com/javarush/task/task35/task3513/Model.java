package com.javarush.task.task35.task3513;

import java.util.ArrayList;
import java.util.List;

/**
 * Содержит игровую логику и хранит игровое поле
 */
public class Model {
    private static final int FIELD_WIDTH = 4;
    private Tile[][] gameTiles;

    public Model() {
        resetGameTiles();
    }

    //Создает новое игровое поле
    protected void resetGameTiles() {
        gameTiles = new Tile[FIELD_WIDTH][FIELD_WIDTH];
        for (int i = 0; i < gameTiles.length; i++) {
            for (int j = 0; j < gameTiles[i].length; j++) {
                gameTiles[i][j] = new Tile();
            }
        }
        addTile();
        addTile();
    }

    //Добавляет на свободную клетку новую плитку со значением 2 или 4 (с соотношением 9 : 1)
    private void addTile() {
        List<Tile> emptyTiles = getEmptyTiles();
        if (!emptyTiles.isEmpty()) {
            int index = (int) (Math.random() * emptyTiles.size()) % emptyTiles.size();
            Tile emptyTile = emptyTiles.get(index);
            emptyTile.value = Math.random() < 0.9 ? 2 : 4;
        }
    }

    //Метод возвращает пустые клетки
    private List<Tile> getEmptyTiles() {
        final List<Tile> result = new ArrayList<>();
        for (int i = 0; i < gameTiles.length; i++) {
            for (int j = 0; j < gameTiles[i].length; j++) {
                if (gameTiles[i][j].isEmpty())
                    result.add(gameTiles[i][j]);
            }
        }
        return result;
    }
}
