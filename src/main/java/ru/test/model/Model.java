package ru.test.model;

import ru.test.controller.Controller;


public class Model {
    private Controller controller;
    private int currentLevel = 5;
    private GameObjects gameObjects;

    public static int FIELD_CELL_SIZE = 15;

    public Model(Controller controller) {
        this.controller = controller;
    }

    public GameObjects getGameObjects() {
        return gameObjects;
    }

    public void restartLevel(int level) {
        this.gameObjects = new GameObjects(level);
    }

    public void restart() {
        restartLevel(currentLevel);
    }

    public void startNextLevel() {
        currentLevel++;
        restart();
    }

    public void markUnit(int x, int y) {
        int i = x / FIELD_CELL_SIZE;
        int j = y / FIELD_CELL_SIZE;
        Unit unit = gameObjects.getUnits()[i][j];
        unit.setMark(unit.isMark()?false:true);
    }

    public void openUnit(int x, int y) {
        int i = x / FIELD_CELL_SIZE;
        int j = y / FIELD_CELL_SIZE;
        Unit unit = gameObjects.getUnits()[i][j];
        if (unit.isMine()) {
            burst();
            return;
        }
        openAllNotMine(i, j);
        checkFinish();
    }

    public void openAllNotMine(int x, int y) {
        Unit[][] units = gameObjects.getUnits();
        int width = units.length - 1;
        int height = units[0].length - 1;

        units[x][y].setOpen(true);
        if (units[x][y].getNumberMine() == 0) {


            if (x < width && !units[x + 1][y].isOpen())
                openAllNotMine(x + 1, y);
            if (y < height && !units[x][y + 1].isOpen())
                openAllNotMine(x, y + 1);
            if (x > 0 && !units[x - 1][y].isOpen())
                openAllNotMine(x - 1, y);
            if (y > 0 && !units[x][y - 1].isOpen())
                openAllNotMine(x, y - 1);

            if (x > 0 && y > 0 && !units[x - 1][y - 1].isOpen())
                openAllNotMine(x - 1, y - 1);
            if (x < width && y > 0 && !units[x + 1][y - 1].isOpen())
                openAllNotMine(x + 1, y - 1);
            if (x < width && y < height && !units[x + 1][y + 1].isOpen())
                openAllNotMine(x + 1, y + 1);
            if (x > 0 && y < height && !units[x - 1][y + 1].isOpen())
                openAllNotMine(x - 1, y + 1);
        }
    }

    private void burst() {
        Unit[][] units = gameObjects.getUnits();
        int width = units.length - 1;
        int height = units[0].length - 1;

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (units[i][j].isMine()) {
                    units[i][j].setMark(true);
                    units[i][j].setOpen(true);
                }
            }
        }
        controller.gameOver();
    }

    private void checkFinish() {
        Unit[][] units = gameObjects.getUnits();
        for (int i = 0; i < units.length - 1; i++) {
            for (int j = 0; j < units[0].length - 1; j++) {
                if (!units[i][j].isOpen() && !units[i][j].isMine())
                    return;
            }
        }
        controller.levelCompleted(currentLevel);
    }
}
