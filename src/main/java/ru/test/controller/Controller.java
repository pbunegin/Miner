package ru.test.controller;


import ru.test.model.GameObjects;
import ru.test.model.Model;
import ru.test.view.View;

public class Controller {
    private View view;
    private Model model;

    public Controller() {
        model = new Model(this);
        model.restart();
        view = new View(this);
        view.init();
    }


    public static void main(String[] args) {
        new Controller();
    }


    public void restart() {
        model.restart();
        view.update();
    }

    public void startNextLevel() {
        model.startNextLevel();
        view = new View(this);
        view.init();
    }

    public void levelCompleted(int level) {
        view.completed(level);
    }

    public void openUnit(int x, int y) {
        model.openUnit(x, y);
        view.update();
    }

    public GameObjects getGameObjects() {
        return model.getGameObjects();
    }

    public void markUnit(int x, int y) {
        model.markUnit(x, y);
        view.update();
    }

    public void gameOver() {
        view.gameOver();
    }
}
