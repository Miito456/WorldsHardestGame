package level1;

import java.awt.*;
import javax.swing.JPanel;

public class Tile extends JPanel {
    public boolean isLeftWall = false, isRightWall = false, isTopWall = false, isBottomWall = false;
    public boolean enemyStart = false;
    
    private static final int BORDER_WIDTH = 3;  
    private Color wallColor = Color.BLACK;  

    public Tile() {
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int width = getWidth();
        int height = getHeight();
        
        g.setColor(wallColor);
        
        if (isTopWall) {
            g.fillRect(0, 0, width, BORDER_WIDTH);
        }
        if (isBottomWall) {
            g.fillRect(0, height - BORDER_WIDTH, width, BORDER_WIDTH);
        }
        if (isLeftWall) {
            g.fillRect(0, 0, BORDER_WIDTH, height);
        }
        if (isRightWall) {
            g.fillRect(width - BORDER_WIDTH, 0, BORDER_WIDTH, height);
        }
    }
}
