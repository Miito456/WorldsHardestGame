package level1;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;

public class GamePanel extends JPanel {

    public static List<Rectangle> safeZones = new ArrayList<>();
    private Rectangle visualRect;
    public static volatile boolean isGameRunning = true;

    public GamePanel() {
        setPreferredSize(new Dimension(800, 600));
        initializeMap();
    }

    int tileSize = 40;

    private void initializeMap() {
        int mapWidth = 20 * tileSize;  
        int mapHeight = 7 * tileSize;  

        setPreferredSize(new Dimension(mapWidth, mapHeight));

        visualRect = new Rectangle(160, 160, 3 * tileSize, mapHeight);
        safeZones.add(new Rectangle(mapWidth - 1 * tileSize, 160, 3 * tileSize, mapHeight));

        addWall(4, 3, 3, 1, false, false, false, true);
        addWall(3, 4, 1, 7, false, true, false, false);
        addWall(4, 11, 5, 1, false, false, true, false);
        addWall(9, 10, 9, 1, true, false, true, false);
        addWall(7, 6, 1, 4, true, true, false, true);
        addWall(8, 5, 9, 1, false, true, false, true);
        addWall(18, 5, 1, 5, true, true, true, false);
        addWall(7, 4, 1, 2, true, false, false, false);
        addWall(22, 4, 1, 7, true, false, false, false);
        addWall(17, 3, 5, 1, false, false, false, true);
        addWall(19, 11, 3, 1, false, false, true, false);
        addWall(16, 4, 1, 1, false, true, false, false);
        addWall(18, 10, 1, 1, false, true, false, false);

        int commonSpeed = 5;
        addEnemy(365, 245, 25, commonSpeed, true);
        addEnemy(635, 365, 25, commonSpeed, false);
        addEnemy(365, 325, 25, commonSpeed, true);
        addEnemy(635, 285, 25, commonSpeed, false);
    }

    private void addWall(int x, int y, int width, int height, boolean left, boolean right, boolean top, boolean bottom) {
        Wall wall = new Wall(x * TILE_SIZE, y * TILE_SIZE, width * TILE_SIZE, height * TILE_SIZE, left, right, top, bottom);
        add(wall);
    }

    private void addEnemy(int x, int y, int diameter, int dx, boolean moveRight) {
        add(new Obstacle(x, y, diameter, dx, moveRight));
    }

    private static final int TILE_SIZE = 40; 
    private static final int GRID_WIDTH = 30 * TILE_SIZE; 
    private static final int GRID_HEIGHT = 18 * TILE_SIZE;  
    private static final Color COLOR_TILE_LIGHT = new Color(247, 247, 255);
    private static final Color COLOR_TILE_DARK = new Color(230, 231, 254);

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int i = 0; i < GRID_WIDTH; i += TILE_SIZE) {
            for (int j = 0; j < GRID_HEIGHT; j += TILE_SIZE) {
                if ((i / TILE_SIZE + j / TILE_SIZE) % 2 == 0) {
                    g.setColor(COLOR_TILE_LIGHT);
                } else {
                    g.setColor(COLOR_TILE_DARK);
                }
                g.fillRect(i, j, TILE_SIZE, TILE_SIZE);
            }
        }

        g.setColor(new Color(183, 254, 181));
        for (Rectangle zone : safeZones) {
            g.fillRect(zone.x, zone.y, zone.width, zone.height);
        }

        g.setColor(new Color(255, 200, 200));
        g.fillRect(visualRect.x, visualRect.y, visualRect.width, visualRect.height);

        g.setColor(Color.BLACK);
        g.drawRect(0, 0, GRID_WIDTH - 1, GRID_HEIGHT - 1);

    }

}
