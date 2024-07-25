package level1;

import Main.Main;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.ArrayList;

public class WorldsHardestGame extends JFrame {

    private GamePanel gamePanel;
    private BackgroundMusic backgroundMusic;
    private Player player;
    private static final int TILE_SIZE = 20;
    private static final int GRID_WIDTH = 15 * TILE_SIZE;
    private static final int GRID_HEIGHT = 15 * TILE_SIZE;
    public static volatile int deathCount = 0;
    public static volatile JLabel deathLabel;

    public WorldsHardestGame() {
        setTitle("World's Hardest Game");
        setSize(1040, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true);

        gamePanel = new GamePanel();
        add(gamePanel, BorderLayout.CENTER);

        player = new Player(200, 250, 25);
        gamePanel.add(player);
        gamePanel.setLayout(null);

        setFocusable(true);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                player.keyPressed(e);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                player.keyReleased(e);
            }
        });

        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(Color.WHITE);
        menuBar.setPreferredSize(new Dimension(getWidth(), 40));

        JMenu menu = new JMenu("Menú");
        menu.setFont(new Font("Yu Gothic UI", Font.PLAIN, 24));
        JMenuItem menuItemBack = new JMenuItem("Regresar al menú principal");
        JMenuItem menuItemRestart = new JMenuItem("Reiniciar nivel");
        JMenuItem menuItemExit = new JMenuItem("Salir");
        menuItemExit.setForeground(Color.BLACK);
        menuItemExit.setFont(new Font("Yu Gothic UI", Font.PLAIN, 24));
        menuItemExit.addActionListener(e -> System.exit(0));

        menuItemBack.setForeground(Color.BLACK);
        menuItemBack.setFont(new Font("Yu Gothic UI", Font.PLAIN, 24));
        menuItemBack.addActionListener(e -> returnToMainMenu());

        menuItemRestart.setForeground(Color.BLACK);
        menuItemRestart.setFont(new Font("Yu Gothic UI", Font.PLAIN, 24));
        menuItemRestart.addActionListener(e -> restartApplication());
        menu.add(menuItemRestart);
        menu.add(menuItemBack);
        menu.add(menuItemExit);
        menuBar.add(menu);

        menuBar.add(Box.createHorizontalGlue());
        menuBar.add(Box.createHorizontalGlue());

        JLabel levelLabel = new JLabel("Nivel 1");
        levelLabel.setForeground(Color.BLACK);
        levelLabel.setFont(new Font("Yu Gothic UI", Font.BOLD, 24));
        menuBar.add(levelLabel);

        menuBar.add(Box.createHorizontalGlue());

        deathLabel = new JLabel(" Muertes: " + deathCount);
        deathLabel.setForeground(Color.BLACK);
        deathLabel.setFont(new Font("Yu Gothic UI", Font.PLAIN, 24));
        menuBar.add(Box.createHorizontalGlue());
        menuBar.add(deathLabel);

        setJMenuBar(menuBar);

        String musicPath = "music.wav";
        backgroundMusic = new BackgroundMusic(musicPath);
        Thread musicThread = new Thread(backgroundMusic);
        musicThread.start();

        setLocationRelativeTo(null);
        setVisible(true);
        Thread playerThread = new Thread(player);
        playerThread.start();
    }

    public void restartApplication() {
        if (backgroundMusic != null) {
            backgroundMusic.stopMusic();
        }
        dispose();
        new WorldsHardestGame().setVisible(true);

    }

    private void returnToMainMenu() {
        if (backgroundMusic != null) {
            backgroundMusic.stopMusic();
        }
        dispose();
        new Main().setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new WorldsHardestGame();
            }
        });
    }
}
