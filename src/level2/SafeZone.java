package level2;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class SafeZone extends Rectangle {
    private static final Color SAFE_ZONE_COLOR = new Color(183, 254, 181);

    public SafeZone(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    public void draw(Graphics g) {
        g.setColor(SAFE_ZONE_COLOR);
        g.fillRect(x, y, width, height);
    }
}
