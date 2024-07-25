package level2;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import javax.swing.JLabel;

public class Coin extends JLabel {
    private int diameter;

    public Coin(int x, int y, int diameter) {
        this.diameter = diameter;
        setBounds(x, y, diameter, diameter);
        setBackground(Color.YELLOW);  
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        int padding = 1; 
        int drawDiameter = diameter - 2 * padding; 

        g2d.setColor(getBackground());
        g2d.fillOval(padding, padding, drawDiameter, drawDiameter);

        g2d.setStroke(new BasicStroke(2)); 
        g2d.setColor(Color.BLACK);
        g2d.drawOval(padding, padding, drawDiameter, drawDiameter);
    }
}
