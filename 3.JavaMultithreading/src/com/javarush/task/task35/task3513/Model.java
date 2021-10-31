package com.javarush.task.task35.task3513;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Содержит игровую логику и хранит игровое поле
 */
public class Model {
    private static final int FIELD_WIDTH = 4;
    private Tile[][] gameTiles;
    int maxTile = 0;
    int score = 0;

    public Model() {
        resetGameTiles();
    }

    //Создает новое игровое поле
    void resetGameTiles() {
        gameTiles = new Tile[FIELD_WIDTH][FIELD_WIDTH];
        for (int i = 0; i < FIELD_WIDTH; i++) {
            for (int j = 0; j < FIELD_WIDTH; j++) {
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
        final List<Tile> list = new ArrayList<>();
        for (Tile[] tileArray : gameTiles) {
            for (Tile t : tileArray)
                if (t.isEmpty()) {
                    list.add(t);
                }
        }
        return list;
    }

    //Метод проводит сжатие плиток путем перемещения пустых плиток вправо
    private boolean compressTiles(Tile[] tiles) {
        int insertPosition = 0;
        boolean isArrayChanged = false;
        for (int i = 0; i < FIELD_WIDTH; i++) {
            if (!tiles[i].isEmpty()) {
                if (i != insertPosition) {
                    tiles[insertPosition] = tiles[i];
                    tiles[i] = new Tile();
                    isArrayChanged = true;
                }
                insertPosition++;
            }
        }
        return isArrayChanged;
    }

    //Метод проводит слияние плиток одного номинала
    private boolean mergeTiles(Tile[] tiles) {
        LinkedList<Tile> tilesList = new LinkedList<>();
        boolean isArrayChanged = false;
        for (int i = 0; i < FIELD_WIDTH; i++) {
            if (tiles[i].isEmpty()) {
                continue;
            }

            if (i < FIELD_WIDTH - 1 && tiles[i].value == tiles[i + 1].value) {
                int updatedValue = tiles[i].value * 2;
                if (updatedValue > maxTile) {
                    maxTile = updatedValue;
                }
                score += updatedValue;
                tilesList.addLast(new Tile(updatedValue));
                tiles[i + 1].value = 0;
                isArrayChanged = true;
            } else {
                tilesList.addLast(new Tile(tiles[i].value));
            }
            tiles[i].value = 0;
        }

        for (int i = 0; i < tilesList.size(); i++) {
            tiles[i] = tilesList.get(i);
        }
        
        return isArrayChanged;
    }

    //Метод вызывает для каждой строки методы сжатия и слияния и добавляет в случае необходимости одну плитку
    public void left() {
        boolean isNeedAddTile = false;
        for (int i = 0; i < FIELD_WIDTH; i++) {
            if (compressTiles(gameTiles[i]) | mergeTiles(gameTiles[i])) {
                isNeedAddTile = true;
            }
        }
        if (isNeedAddTile) {
            addTile();
        }
    }
}
