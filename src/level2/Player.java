package level2;

import Main.Main;
import javax.swing.*;
import java.util.HashSet;
import java.util.Set;
import java.awt.Color;
import java.awt.Component;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.BasicStroke;

public class Player extends JLabel implements Runnable {

    private int x, y, size;
    public Set<Integer> keysPressed = new HashSet<>();
    private static final int MOVE_INCREMENT = 2;
    private static final Color COLOR_PLAYER = new Color(251, 3, 1);
    private boolean hasCoin = false;  

    
    public Player(int x, int y, int size) {
        this.x = x;
        this.y = y;
        this.size = size;
        setBounds(x, y, size, size);
        setBackground(COLOR_PLAYER);
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create(); 

        g2d.setColor(getBackground());
        g2d.fillRect(0, 0, size, size); 

        g2d.setColor(Color.BLACK); 
        g2d.setStroke(new BasicStroke(5)); 
        g2d.drawRect(1, 1, size - 3, size - 3); 

        g2d.dispose();
    }

    public void keyPressed(KeyEvent e) {
        keysPressed.add(e.getKeyCode());
        movePlayer();
    }

    public void keyReleased(KeyEvent e) {
        keysPressed.remove(e.getKeyCode());
    }

    private void movePlayer() {
        int newX = x;
        int newY = y;

        if (keysPressed.contains(KeyEvent.VK_LEFT)) {
            newX -= MOVE_INCREMENT;
        }
        if (keysPressed.contains(KeyEvent.VK_RIGHT)) {
            newX += MOVE_INCREMENT;
        }
        if (keysPressed.contains(KeyEvent.VK_UP)) {
            newY -= MOVE_INCREMENT;
        }
        if (keysPressed.contains(KeyEvent.VK_DOWN)) {
            newY += MOVE_INCREMENT;
        }

        if (canMove(newX, newY)) {
            x = newX;
            y = newY;
            updatePosition();
            if (hasCoin) {  
                checkSafeZone();
            }
        }
    }

    private void checkSafeZone() {
        if (!WorldsHardestGame2.player.hasCoin()) {
            return;  
        }
        Rectangle playerBounds = new Rectangle(x, y, size, size);
        for (Rectangle safeZone : GamePanel.safeZones) {
            if (playerBounds.intersects(safeZone)) {
                GamePanel.isGameRunning = false;  
                showLevelCompletedMenu();  
                break;
            }
        }
    }

    private boolean menuShown = false; 

    private void showLevelCompletedMenu() {
        if (!menuShown) {
            menuShown = true; 
            SwingUtilities.invokeLater(() -> {
                Object[] options = {"Reiniciar Nivel", "Volver al Menú Principal"};
                int choice = JOptionPane.showOptionDialog(null, "Completaste el nivel", "Nivel Completado",
                        JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
                if (choice == JOptionPane.YES_OPTION) {
                    WorldsHardestGame2.deathCount = 0;
                    restartApplication();
                    JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
                    if (frame != null) {
                        frame.dispose();  
                    }
                } else if (choice == JOptionPane.NO_OPTION) {
                    returnToMainMenu();
                }
            });
        }
    }

    public void restartApplication() {
        menuShown = false; 
        WorldsHardestGame2.main(new String[0]);
        GamePanel.isGameRunning = true;

    }

    private void returnToMainMenu() {
        menuShown = false; 
        new Main().setVisible(true);
        JFrame mainFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        mainFrame.dispose();  

    }

    public boolean hasCoin() {
        return hasCoin;
    }

    public void setHasCoin(boolean hasCoin) {
        this.hasCoin = hasCoin;
    }

    private boolean canMove(int newX, int newY) {
        Rectangle proposedBounds = new Rectangle(newX, newY, size, size);
        for (Component comp : getParent().getComponents()) {
            if (comp instanceof Wall) {
                Rectangle wallBounds = ((Wall) comp).getBounds();
                if (proposedBounds.intersects(wallBounds)) {
                    return false;  
                }
            }
        }
        return true;
    }

    private void updatePosition() {
        SwingUtilities.invokeLater(() -> {
            setBounds(x, y, size, size);
        });
    }

    @Override
    public void run() {
        while (GamePanel.isGameRunning) {
            try {
                movePlayer();  
                checkCollisions();
                Thread.sleep(10);  
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void checkCollisions() {
        Rectangle playerBounds = new Rectangle(x, y, size, size);
        for (Component comp : getParent().getComponents()) {
            if (comp instanceof Obstacle) {
                Rectangle obstacleBounds = comp.getBounds();
                if (playerBounds.intersects(obstacleBounds)) {
                    handleCollision();
                    break;
                }
            }
            if (comp instanceof Wall) {
                Rectangle wallBounds = comp.getBounds();
                if (playerBounds.intersects(wallBounds)) {
                    handleCollisionWithWall();
                    break;
                }
            }

            if (comp instanceof Coin && !hasCoin) {
                Rectangle coinBounds = comp.getBounds();
                if (playerBounds.intersects(coinBounds)) {
                    handleCoinCollision((Coin) comp);
                    break;
                }
            }
            if (hasCoin) {
                checkSafeZone();  
            }
        }
    }

    private void handleCoinCollision(Coin coin) {
        coin.setVisible(false);  
        WorldsHardestGame2.player.setHasCoin(true);  
    }

    private void handleCollisionWithWall() {
        x = 40;
        y = 40;
        updatePosition();
    }

    private void handleCollision() {
        x = 200;
        y = 250;
        updatePosition();
        incrementDeathCount();
    }

    public void incrementDeathCount() {
        WorldsHardestGame2.deathCount++;
        WorldsHardestGame2.deathLabel.setText(" Muertes: " + WorldsHardestGame2.deathCount);
    }

}
