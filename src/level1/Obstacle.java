package level1;

import java.awt.*;
import javax.swing.*;

public class Obstacle extends JLabel implements Runnable {

    private int diameter;
    private int dx; 
    private static final Color COLOR_ENEMY = new Color(14, 1, 219);

    public Obstacle(int x, int y, int diameter, int dx, boolean moveRight) {
        this.diameter = diameter;
        this.dx = moveRight ? Math.abs(dx) : -Math.abs(dx);  
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
                int limit = getParent().getWidth() - this.diameter;
                int newX = getX() + dx;

                boolean collisionWithWall = false;
                Rectangle newBounds = new Rectangle(newX, getY(), diameter, diameter);
                for (Component comp : getParent().getComponents()) {
                    if (comp instanceof Wall) {
                        Rectangle wallBounds = comp.getBounds();
                        if (newBounds.intersects(wallBounds)) {
                            collisionWithWall = true;
                            break;
                        }
                    }
                }

                if (newX < 0 || newX > limit || collisionWithWall) {
                    dx *= -1;
                    newX = getX() + dx;
                }

                setBounds(newX, getY(), diameter, diameter);
                Thread.sleep(15); 
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

}
