package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GameField extends JPanel implements ActionListener {
    private final int DOT_SIZE = 16;
    private final int SIZE = 640;
    private final int ALL_DOTS = 500;

    private Image snakeImg;
    private Image appleImg;

    private Timer timer;

    private int[] snakeX = new int[ALL_DOTS];
    private int[] snakeY = new int[ALL_DOTS];

    private int appleX;
    private int appleY;

    private int dots = 3;

    private boolean isLeft = false;
    private boolean isRight = true;
    private boolean isUp = false;
    private boolean isDown = false;
    private boolean isAlive = true;

    private int score;

    public GameField() {
        setBackground(Color.BLACK);
        initGame();
        loadImages();
        addKeyListener(new GameKeyListener());
        setFocusable(true);
    }


    private void move() {
        for(int i = dots; i > 0; i--) {
            snakeX[i] = snakeX[i-1];
            snakeY[i] = snakeY[i-1];
        }

        if(isLeft) {
            snakeX[0] -= DOT_SIZE;
        } else if(isRight) {
            snakeX[0] += DOT_SIZE;
        } else if(isUp) {
            snakeY[0] -= DOT_SIZE;
        } else if(isDown) {
            snakeY[0] += DOT_SIZE;
        }
    }
    private void checkApple() {
        if(snakeX[0] == appleX && snakeY[0] == appleY) {
            dots+=1;
            score += 16;
            createApple();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if(isAlive) {
            g.setColor(Color.white);
            g.drawImage(appleImg, appleX, appleY, this);
            for(int i = 0; i < dots; i++) {
                g.drawImage(snakeImg, snakeX[i], snakeY[i], this);
            }
            g.drawString("Score: " + score, 16, 16);
        } else {
            g.setColor(Color.white);
            g.drawString("Game over", SIZE / 2, SIZE / 2);
            g.drawString("Score: " + score, 16, 16);
        }
    }

    public void checkCollisions() {
        for(int i = dots; i > 0; i--) {
            if(snakeX[0] == snakeX[i] && snakeY[0] == snakeY[i]) {
                isAlive = false;
            }

            if(snakeX[0] > SIZE - 32) {
                isAlive = false;

            } else if(snakeX[0] < 0) {
                isAlive = false;
            } else if(snakeY[0] > SIZE - 32) {
                isAlive = false;
            } else if(snakeY[0] < 0) {
                isAlive = false;
            }
        }
    }

    private void initGame() {
        for(int i = 0; i < dots; i++) {
            snakeX[i] = 48 - i * 16;
            snakeY[i] = 48;
        }

        timer = new Timer(150, this); // MySnake
        timer.start();
        createApple();
    }


    private void loadImages() {
        ImageIcon aImg = new ImageIcon("myApple.png");
        ImageIcon sImg = new ImageIcon("mySnake.png");

        appleImg = aImg.getImage();
        snakeImg = sImg.getImage();
    }
    private void createApple() {
        appleX = new Random().nextInt(20) * 16;
        appleY = new Random().nextInt(20) * 16;

        for(int x : snakeX) {
            if(x == appleX) {
                appleX = new Random().nextInt(20) * 16;
                return;
            }
        }

        for(int y : snakeY) {
            if(y == appleY) {
                appleY = new Random().nextInt(20) * 16;
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(isAlive) {
            checkApple();
            checkCollisions();
            move();
        }
        repaint();
    }


    public class GameKeyListener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);

            int key = e.getKeyCode();

            if(key == KeyEvent.VK_W && !isDown) {
                isUp = true;
                isLeft = false;
                isRight = false;
            } else if(key == KeyEvent.VK_S && !isUp) {
                isRight = false;
                isLeft = false;
                isDown = true;
            } else if(key == KeyEvent.VK_D && !isLeft) {
                isRight = true;
                isUp = false;
                isDown = false;
            } else if(key == KeyEvent.VK_A && !isRight) {
                isLeft = true;
                isUp = false;
                isDown = false;
            } if(key == KeyEvent.VK_R) {
                isAlive = true;
            }
        }
    }
}
