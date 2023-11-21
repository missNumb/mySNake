package org.example;

import javax.swing.*;

public class MainWindow extends JFrame {

    public MainWindow() {
        setTitle("MySnake");
        setSize(640, 640);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        add(new GameField());
        setLocation(450, 100);
    }

    public static void main(String[] args) {
        new MainWindow();
    }
}