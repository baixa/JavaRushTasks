package com.javarush.task.task35.task3513;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

/**
 * Содержит игровую логику и хранит игровое поле
 */
public class Model {
    private static final int FIELD_WIDTH = 4;
    private Tile[][] gameTiles;
    int maxTile = 0;
    int score = 0;
    private boolean isSaveNeeded = true;
    private Stack<Tile[][]> previousStates = new Stack<>();
    private Stack<Integer> previousScores = new Stack<>();

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
        boolean result = false;
        for (int i = 0; i < FIELD_WIDTH; i++) {
            if (!tiles[i].isEmpty()) {
                if (i != insertPosition) {
                    tiles[insertPosition] = tiles[i];
                    tiles[i] = new Tile();
                    result = true;
                }
                insertPosition++;
            }
        }
        return result;
    }

    //Метод проводит слияние плиток одного номинала
    private boolean mergeTiles(Tile[] tiles) {
        boolean result = false;
        LinkedList<Tile> tilesList = new LinkedList<>();
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
                result = true;
            } else {
                tilesList.addLast(new Tile(tiles[i].value));
            }
            tiles[i].value = 0;
        }

        for (int i = 0; i < tilesList.size(); i++) {
            tiles[i] = tilesList.get(i);
        }

        return result;
    }

    //Поворачиваем массив по часовой стрелке на 90 градусов
    private Tile[][] rotateClockwise(Tile[][] tiles) {
        final int N = tiles.length;
        Tile[][] result = new Tile[N][N];
        for (int r = 0; r < N; r++) {
            for (int c = 0; c < N; c++) {
                result[c][N - 1 - r] = tiles[r][c];
            }
        }
        return result;
    }

    //Метод выполняет смещение клеток при перемещении их влево
    public void left() {
        if (isSaveNeeded)
            saveState(gameTiles);
        boolean isNeedAddTile = false;
        for (int i = 0; i < FIELD_WIDTH; i++) {
            if (compressTiles(gameTiles[i]) | mergeTiles(gameTiles[i])) {
                isNeedAddTile = true;
            }
        }
        if (isNeedAddTile) {
            addTile();
        }
        isSaveNeeded = true;
    }

    //Метод выполняет смещение клеток при перемещении их вправо
    public void right() {
        saveState(gameTiles);
        gameTiles = rotateClockwise(gameTiles);
        gameTiles = rotateClockwise(gameTiles);
        left();
        gameTiles = rotateClockwise(gameTiles);
        gameTiles = rotateClockwise(gameTiles);
    }

    //Метод выполняет смещение клеток при перемещении их вверх
    public void up() {
        saveState(gameTiles);
        gameTiles = rotateClockwise(gameTiles);
        gameTiles = rotateClockwise(gameTiles);
        gameTiles = rotateClockwise(gameTiles);
        left();
        gameTiles = rotateClockwise(gameTiles);
    }

    //Метод выполняет смещение клеток при перемещении их вниз
    public void down() {
        saveState(gameTiles);
        gameTiles = rotateClockwise(gameTiles);
        left();
        gameTiles = rotateClockwise(gameTiles);
        gameTiles = rotateClockwise(gameTiles);
        gameTiles = rotateClockwise(gameTiles);
    }

    public Tile[][] getGameTiles() {
        return gameTiles;
    }

    //Метод возвращает булево значение, описывающее, возможно ли совершить действие, которое изменит игровое поле
    public boolean canMove() {
        if (getEmptyTiles().size() == 0) {
            for (int i = 0; i < gameTiles.length-1; i++) {
                for (int j = 0; j < gameTiles[i].length-1; j++) {
                    if (gameTiles[i][j].value == gameTiles[i][j+1].value || gameTiles[i][j].value == gameTiles[i+1][j].value){
                        return true;
                    }
                }
            }
            return false;
        }
        return true;
    }

    //Метод сохраняет текущее состояние игры и счет в стек
    private void saveState(Tile[][] tiles) {
        Tile[][] currentTiles = new Tile[FIELD_WIDTH][FIELD_WIDTH];
        for (int i = 0; i < currentTiles.length; i++) {
            for (int j = 0; j < currentTiles[i].length; j++) {
                currentTiles[i][j] = new Tile(tiles[i][j].value);
            }
        }
        previousStates.push(currentTiles);
        previousScores.push(score);
        isSaveNeeded = false;
    }

    //Метод возвращает предыдущее игровое состояние
    public void rollback() {
        if (previousStates.isEmpty() || previousScores.isEmpty())
            return;

        gameTiles = previousStates.pop();
        score = previousScores.pop();
    }
}
