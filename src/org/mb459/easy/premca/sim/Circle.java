/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mb459.easy.premca.sim;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

/**
 *
 * @author Miles
 */
public class Circle {
    protected final float radius;
    protected final Ellipse2D.Float circle;
    protected final int lBound, rBound;

    public Circle(Point2D.Float initLoc, int radius, int lBound, int rBound) {
        circle = new Ellipse2D.Float();
        this.radius = radius;
        this.lBound = lBound;
        this.rBound = rBound;
        circle.setFrameFromCenter(initLoc.x, initLoc.y, initLoc.x - radius, initLoc.y - radius);
    }

    /**
     * Moves the circle by x and y amounts. Clips to left = 0 and right = rBound.
     * @param x
     * @param y
     * @param rBound
     */
    public void move(float x, float y) {
        float newX = (float) circle.getCenterX();
        float newY = (float) circle.getCenterY();
        if (newX + x > 0 & newX + x < rBound - getRadius()) {
            newX += x;
        }
        newY += y;
        circle.setFrameFromCenter(newX, newY, newX - getRadius(), newY - getRadius());
    }

    /**
     * Draws the circle on the specified Graphics2D converting to actual values from grid.
     * @param g2 the Graphics2D object to draw on
     */
    public void draw(Graphics2D g2) {
        g2.setColor(Color.BLACK);
        g2.fill(circle);
    }

    /**
     * @return The circle radius, in world units
     */
    public float getRadius() {
        return radius;
    }

    public float getCenterX() {
        return (float) circle.getCenterX();
    }

    public float getCenterY() {
        return (float) circle.getCenterY();
    }

    public float getX() {
        return circle.x;
    }

    public float getY() {
        return circle.y;
    }
    
}
