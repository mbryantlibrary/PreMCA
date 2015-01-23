/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mb459.easy.premca.sim;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

/**
 * Represents a sensor unit, which detects collisions with a circle and has activations.
 * @author Miles
 */
public class Sensor extends Line2D.Float {
    private Point location1 = new Point(); //base point
    private final float length;
    private float angle;
    private int act = 0; //get amount of activation
    
    /**
     * Creates a new sensor with a base point, angle and length
     * @param base
     * @param angle radians (in multiples of PI)
     * @param length 
     */
    public Sensor(Point2D.Float base,float angle, float length) {
        this.setLine(
                base.x, 
                base.y, 
                base.x + Math.cos(angle * Math.PI)* length, 
                base.y - Math.sin(angle * Math.PI) * length
        );
        this.length = length;
    }
    
    /**
     * move end point sideways; base point should be moved by agent
     * @param x 
     */
    public void move(int x) {
        this.setLine(
                getX1() + x, 
                getY1(), 
                getX2() + x, 
                getY2()
        );
    }
    /**
     * Sets activation state based on the location of the circle given.
     * @param circle 
     */
    public void doCollision(Circle circle) {
        //This version utilises Java awt geometry API to speed up calculations
        //check whether the distance between circle center and closest point on 
        //line is less than circle radius
        
        if(Math.abs(this.ptSegDist(circle.getCenterX(),circle.getCenterY())) < circle.getRadius()) {
            act = getActLevel(new Point2D.Float((float)circle.getCenterX(), (float)circle.getCenterY()));
        } else {
            //no activation
            act = 0;
        }
    }
    
    /**
     * gets amount of activation based on circle's distance
     * @param circleCenter
     * @return 
     */
    private int getActLevel(Point2D.Float circleCenter) {
        float dist = (float)circleCenter.distance(getP1());
        if(circleCenter.y > getY1())
            //circle is below agent, no activation
            dist = 10;
        else {
            // get activation out of 10 based on distance
            dist = (dist * 10) / length; 
            if(dist > 10) //clip at 10
                dist = 10;
            }
        return (int)Math.round(10 - dist);
    }
    
    /**
     * draws this sensor to specified Graphics2D
     * @param g2
     */
    public void draw(Graphics2D g2) {
        
        if(getAct() > 0)
            //activated, colour with a shade of red to show activation
            g2.setColor(new Color((int)Math.round(getAct() * 25.5),0,0));
        else
            g2.setColor(Color.BLACK);

        g2.draw(this);
    }

    /**
     * @return the act
     */
    public int getAct() {
        return act;
    }
        
}
