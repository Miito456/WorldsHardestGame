package level2;

import java.awt.*;
import javax.swing.*;

public class Obstacle extends JLabel implements Runnable {

    private int diameter;
    private int dx;
    private int dy;
    private static final Color COLOR_ENEMY = new Color(14, 1, 219);

    public Obstacle(int x, int y, int diameter, int dx, int dy) {
        this.diameter = diameter;
        this.dx = dx;
        this.dy = dy;
        setBounds(x, y, diameter, diameter);
        setBackground(COLOR_ENEMY);
        setOpaque(false);
        new Thread(this).start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        int padding = 1;
        int drawDiameter = diameter - 2 * padding;

        g2d.setColor(COLOR_ENEMY);
        g2d.fillOval(padding, padding, drawDiameter, drawDiameter);

        g2d.setStroke(new BasicStroke(2));
        g2d.setColor(Color.BLACK);
        g2d.drawOval(padding, padding, drawDiameter, drawDiameter);
    }

    @Override
    public void run() {
        while (GamePanel.isGameRunning) {
            try {
                int xLimit = getParent().getWidth() - this.diameter;
                int yLimit = getParent().getHeight() - this.diameter;
                int newX = getX() + dx;
                int newY = getY() + dy;

                boolean collisionX = false;
                Rectangle newBoundsX = new Rectangle(newX, getY(), diameter, diameter);
                for (Component comp : getParent().getComponents()) {
                    if (comp instanceof Wall) {
                        Rectangle wallBounds = comp.getBounds();
                        if (newBoundsX.intersects(wallBounds)) {
                            collisionX = true;
                            break;
                        }
                    }
                }

                boolean collisionY = false;
                Rectangle newBoundsY = new Rectangle(getX(), newY, diameter, diameter);
                for (Component comp : getParent().getComponents()) {
                    if (comp instanceof Wall) {
                        Rectangle wallBounds = comp.getBounds();
                        if (newBoundsY.intersects(wallBounds)) {
                            collisionY = true;
                            break;
                        }
                    }
                }

                if (newX < 0 || newX > xLimit || collisionX) {
                    dx = -dx;
                }
                if (newY < 0 || newY > yLimit || collisionY) {
                    dy = -dy;
                }

                setBounds(getX() + dx, getY() + dy, diameter, diameter);
                Thread.sleep(15);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

}
