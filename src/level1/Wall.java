package level1;

import java.awt.*;
import javax.swing.*;

public class Wall extends JPanel {
    private boolean isLeftWall, isRightWall, isTopWall, isBottomWall;
    private static final int BORDER_THICKNESS = 3; 

    public Wall(int x, int y, int width, int height,
                boolean left, boolean right, boolean top, boolean bottom) {
        setBounds(x, y, width, height);
        this.isLeftWall = left;
        this.isRightWall = right;
        this.isTopWall = top;
        this.isBottomWall = bottom;
        setBackground(new Color(0, 0, 0)); 
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(getBackground());
        int w = getWidth();
        int h = getHeight();

        if (isTopWall) {
            g.fillRect(0, 0, w, BORDER_THICKNESS);
        }
        if (isBottomWall) {
            g.fillRect(0, h - BORDER_THICKNESS, w, BORDER_THICKNESS);
        }
        if (isLeftWall) {
            g.fillRect(0, 0, BORDER_THICKNESS, h);
        }
        if (isRightWall) {
            g.fillRect(w - BORDER_THICKNESS, 0, BORDER_THICKNESS, h);
        }
    }
}
