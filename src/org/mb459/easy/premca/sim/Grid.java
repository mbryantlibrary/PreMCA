package org.mb459.easy.premca.sim;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;


/**
 * Represents a virtual in world grid (as opposed to a grid for drawing on the screen).
 * @author Miles
 */
public class Grid extends JPanel{
    private final int w,h; //width, height
    private final float dx,dy; //conversion parameters to real screen
    private boolean paintGrid = false; //whether to paint grid
    /**
     * Initialises a new virtual grid (with no screen equivalent).
     * @param width
     * @param height 
     */
    public Grid(int width, int height) {
        w = width;h = height;
        
        //no screen equivalent, but set these to prevent null errors
        Dimension screenSize = new Dimension(width,height);
        this.setSize(screenSize);
        dx = 1;
        dy = 1;
    }
    
    /**
     * Initialises a new virtual grid with a screen equivalent.
     * @param width
     * @param height
     * @param screenSize size of drawing panel
     */
    public Grid(int width, int height,Dimension screenSize) {
        w = width;h = height;
        this.setSize(screenSize);
        dx = (float)screenSize.width / (float)w;
        dy = (float)screenSize.height / (float)h;
    }
    
    /**
     * paints the grid if set
     * @param g 
     */
    @Override
    public void paint(Graphics g) {
        super.paintComponent(g);
        if(!isPaintGrid())
            return;
        Graphics2D g2 = (Graphics2D)g;
        g2.setColor(Color.GRAY);
        for(int x = 1; x < w;x++) {
            g2.drawLine(
                    (int)Math.round((float)x * dx),
                    0, 
                    (int)Math.round((float)x * dx), 
                    getSize().height
            );
        }
        for(int y = 1; y < h; y++) {
            g2.drawLine(
                    0,
                    (int)Math.round((float)y * dy),
                    getSize().width, 
                    (int)Math.round((float)y * dy)
            );
        }
    }
    
    private int getActualN(float n,boolean isX) {
        return (int)Math.round(n * (isX ? dx : dy));
    }


    /**
     * @return the paintGrid
     */
    public boolean isPaintGrid() {
        return paintGrid;
    }

    /**
     * @param paintGrid the paintGrid to set
     */
    public void setPaintGrid(boolean paintGrid) {
        this.paintGrid = paintGrid;
    }
    
    
}
