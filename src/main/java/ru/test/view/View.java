package ru.test.view;


import ru.test.controller.Controller;
import ru.test.model.GameObjects;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class View extends JFrame {
    private Controller controller;
    private Field field;

    public View(Controller controller) {
        this.controller = controller;
    }

    public void init() {
        field = new Field(this);
        field.setController(controller);
        field.setPreferredSize(new Dimension((getGameObjects().getUnits().length) * getGameObjects().getUnits()[0][0].getWidth()+1,
                (getGameObjects().getUnits()[0].length) * getGameObjects().getUnits()[0][0].getHeight()+1));
//        JPanel jPanel = new JPanel();
//        jPanel.add(field);
        add(field);

        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Игра");
        JMenuItem menuItem1 = new JMenuItem("Начать заново");
        JMenuItem menuItem2 = new JMenuItem("Выход");
        menuItem1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.restart();
            }
        });
        menuItem2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        menu.add(menuItem1);
        menu.addSeparator();
        menu.add(menuItem2);
        menuBar.add(menu);
        setJMenuBar(menuBar);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        pack();
        setResizable(false);
        setLocationRelativeTo(null);
        setTitle("Сапёр");
        setVisible(true);
    }

    public void update() {
        field.repaint();
    }

    public GameObjects getGameObjects() {
        return controller.getGameObjects();
    }

    public void completed(int level) {
        update();
        int result = JOptionPane.showConfirmDialog(this, "Уровень завершен!","Молодец",
                JOptionPane.YES_NO_OPTION);

        if (result == JOptionPane.YES_OPTION)
            controller.startNextLevel();
        else if (result == JOptionPane.NO_OPTION)
            controller.restart();
    }

    public void gameOver() {
        update();
        JOptionPane.showMessageDialog(this, "Вы проиграли.");
        controller.restart();
    }
}
