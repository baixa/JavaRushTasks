package com.javarush.task.task35.task3513;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Следит за нажатием клавиш во время игры
 */
public class Controller extends KeyAdapter {
    private Model model;
    private View view;
    final private static int WINNING_TILE = 2048;

    public Controller(Model model) {
        this.model = model;
        view = new View(this);
    }

    public Tile[][] getGameTiles() {
        return model.getGameTiles();
    }

    public int getScore() {
        return model.score;
    }

    //Сброс игры
    public void resetGame() {
        model.score = 0;
        view.isGameLost = false;
        view.isGameWon = false;
        model.resetGameTiles();
    }

    //Обработчик нажатий клавиш
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
            resetGame();

        if (!model.canMove())
            view.isGameLost = true;

        if (!view.isGameLost && !view.isGameWon) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT -> model.left();
                case KeyEvent.VK_RIGHT -> model.right();
                case KeyEvent.VK_UP -> model.up();
                case KeyEvent.VK_DOWN -> model.down();
            }
        }

        if (model.maxTile == WINNING_TILE)
            view.isGameWon = true;

        view.repaint();
    }
}
