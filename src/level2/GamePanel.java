package level2;

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

        visualRect = new Rectangle(160, 240, 3 * tileSize, 80);
        safeZones.add(new Rectangle(mapWidth - 1 * tileSize, 240, 3 * tileSize, 80));

        addWall(3, 6, 1, 2, false, true, false, false);
        addWall(7, 10, 12, 1, false, false, true, false);
        addWall(7, 3, 12, 1, false, false, false, true);
        addWall(6, 8, 1, 2, false, true, false, false);
        addWall(6, 4, 1, 2, false, true, false, false);

        addWall(19, 4, 1, 2, true, false, false, false);
        addWall(19, 8, 1, 2, true, false, false, false);
        addWall(22, 6, 1, 2, true, false, false, false);
        addWall(4, 5, 3, 1, false, false, false, true);
        addWall(4, 8, 3, 1, false, false, true, false);
        addWall(19, 5, 3, 1, false, false, false, true);
        addWall(19, 8, 3, 1, false, false, true, false);

        int commonSpeed = 5;
        addObstacle(290, 165, 20, 0, commonSpeed);  
        addObstacle(370, 165, 20, 0, commonSpeed);  
        addObstacle(450, 165, 20, 0, commonSpeed);  
        addObstacle(530, 165, 20, 0, commonSpeed);  
        addObstacle(610, 165, 20, 0, commonSpeed);  
        addObstacle(690, 165, 20, 0, commonSpeed);  

        addObstacle(330, 385, 20, 0, commonSpeed);  
        addObstacle(410, 385, 20, 0, commonSpeed);  
        addObstacle(490, 385, 20, 0, commonSpeed);  
        addObstacle(570, 385, 20, 0, commonSpeed);  
        addObstacle(650, 385, 20, 0, commonSpeed);  
        addObstacle(730, 385, 20, 0, commonSpeed);  

        addCoin(510, 270, 20);

    }

    private void addCoin(int x, int y, int diameter) {
        Coin coin = new Coin(x, y, diameter);
        add(coin);
    }

    private void addWall(int x, int y, int width, int height, boolean left, boolean right, boolean top, boolean bottom) {
        Wall wall = new Wall(x * TILE_SIZE, y * TILE_SIZE, width * TILE_SIZE, height * TILE_SIZE, left, right, top, bottom);
        add(wall);
    }

    private void addObstacle(int x, int y, int diameter, int dx, int dy) {
        Obstacle obstacle = new Obstacle(x, y, diameter, dx, dy);
        add(obstacle);
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
