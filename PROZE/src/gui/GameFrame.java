package gui;
import helpfulTools.ColorTranslator;
import configuration.Configer;
import configuration.Leveler;
import gameLogic.Cannon;
import gameLogic.Enemy;
import gameLogic.GameObjectList;
import spaceInvaders.Game;

import java.awt.*;
import java.awt.event.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.*;

/** Klasa odpowiadajaca za okno gry */
public class GameFrame extends JFrame implements KeyListener{

    private final GameObjectList gameObjectList;
    private final GameCanvas gameCanvas;
    private final Cannon cannon;

    /** Metoda zwracająca obiekt klasy GameCanvas */
    public GameCanvas getGameCanvas() {
        return gameCanvas;
    }

    /** Konstruktor klasy GameFrame */
    public GameFrame(Game game) {
        gameObjectList = new GameObjectList();
        Configer confer = game.getConfiger();
        setTitle(confer.getGameTitle());
        ColorTranslator color = new ColorTranslator();
        Leveler lvl1 = game.getLeveler();

        cannon = game.getCannon();

        Enemy enemy1 = new Enemy(lvl1.getEnemy1XScreenPosition(), lvl1.getEnemy1YScreenPosition(), confer.getEnemyWidth(), confer.getEnemyHeight(), color.translateColor(lvl1.getColorEnemy()), confer.getEnemyLives());
        Enemy enemy2 = new Enemy(lvl1.getEnemy2XScreenPosition(), lvl1.getEnemy2YScreenPosition(), confer.getEnemyWidth(), confer.getEnemyHeight(), color.translateColor(lvl1.getColorEnemy()),  confer.getEnemyLives());
        Enemy enemy3 = new Enemy(lvl1.getEnemy3XScreenPosition(), lvl1.getEnemy3YScreenPosition(), confer.getEnemyWidth(), confer.getEnemyHeight(), color.translateColor(lvl1.getColorEnemy()),  confer.getEnemyLives());
        Enemy enemy4 = new Enemy(lvl1.getEnemy4XScreenPosition(), lvl1.getEnemy4YScreenPosition(), confer.getEnemyWidth(), confer.getEnemyHeight(), color.translateColor(lvl1.getColorEnemy()),  confer.getEnemyLives());
        gameObjectList.add(enemy1);
        gameObjectList.add(enemy2);
        gameObjectList.add(enemy3);
        gameObjectList.add(enemy4);

        setLayout(new BorderLayout());

        Panel topPanel = new Panel(new BorderLayout());
        Panel buttonPanel = new Panel(new FlowLayout());
        Panel bottomPanel = new Panel(new BorderLayout());
        Panel canvasPanel = new Panel(new BorderLayout());
        Panel pointsPanel = new Panel(new FlowLayout());
        Panel livesPanel = new Panel(new FlowLayout());

        Label pointsLabel = new Label(confer.getLabelPoints());
        Label pointsAmount = new Label(Integer.toString(confer.getInitialPoints()));
        Label livesLabel = new Label(confer.getLabelLivesLeft());
        Label livesAmount = new Label(Integer.toString(confer.getInitialLives()));

        Button pauseButton = new Button(confer.getButtonStartText());
        Button exitButton = new Button(confer.getButtonEndText());

        exitButton.addActionListener(e -> System.exit(1));
        pauseButton.addActionListener(e -> {
            if (game.getAnimation() == null) {
                game.startAnimation();
                pauseButton.setLabel(confer.getButtonPauseText());
                canvasPanel.requestFocus();
            } else {
                game.stopAnimation();
                pauseButton.setLabel(confer.getButtonStartText());
            }
            pack();
        });
        buttonPanel.add(pauseButton);
        buttonPanel.add(exitButton);

        pointsPanel.add(pointsLabel);
        pointsPanel.add(pointsAmount);

        livesPanel.add(livesLabel);
        livesPanel.add(livesAmount);

        topPanel.add(buttonPanel, BorderLayout.EAST);
        topPanel.add(pointsPanel, BorderLayout.WEST);

        canvasPanel.add(gameCanvas = new GameCanvas(color.translateColor(lvl1.getColorBackground()), gameObjectList, cannon, game), BorderLayout.CENTER);
        canvasPanel.addKeyListener(this);
        bottomPanel.add(livesPanel, BorderLayout.WEST);

        add(topPanel, BorderLayout.NORTH);
        add(canvasPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        addWindowListener(new WindowAdapter() { public void windowClosing(WindowEvent e) { System.exit(0);}});

        addComponentListener(new ComponentAdapter() { @Override public void componentResized(ComponentEvent e) { gameCanvas.setPreferredSize(gameCanvas.getSize()); } });

        setFocusable(true);
        setFocusTraversalKeysEnabled(true);
        addKeyListener(this);

        pack();
    }

    /** Metoda zwracajaca obiekt klasy GameObjectList */
    public GameObjectList getGameObjectList() {
        return gameObjectList;
    }

    private typeOfMove cannonState;

    private enum typeOfMove {
        LEFT,
        RIGHT,
        STOPPED
    }

    private void setMovementState(typeOfMove state) {
        cannonState = state;
    }

    /** Metoda do poruszania dzialem gracza */
    public void moveCannon() {
        float stepX = 0.01f;
        if (cannonState == typeOfMove.LEFT) {
            if (Float.compare(cannon.getX(), 0f) > 0) {
                cannon.setX(cannon.getX() - stepX);
            }
        } else if (cannonState == typeOfMove.RIGHT) {
            if (Float.compare(cannon.getX() + stepX + cannon.getWidth(), 1f) < 0) {
                cannon.setX(cannon.getX() + stepX);
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        switch (keyEvent.getKeyCode()) {
            case KeyEvent.VK_LEFT -> {
                setMovementState(typeOfMove.LEFT);
                moveCannon();
            }
            case KeyEvent.VK_RIGHT -> {
                setMovementState(typeOfMove.RIGHT);
                moveCannon();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        setMovementState(typeOfMove.STOPPED);
    }

}