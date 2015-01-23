/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mb459.easy.premca.sim;

import org.mb459.easy.premca.genesis.AgentGenotype;
import org.mb459.easy.premca.exp.ExpParam;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

/**
 * Represents and integrates the world of a single trial.
 * @author Miles
 */
public class World {
    
    private Agent agent; 
    private Circle circle;
    private Dimension gridSize = new Dimension(400,275);
    private float circleDX = 0; //circle horizontal velocity
    private final float circleDY = 3; //circle vertical velocity
    private final int BOUNDOFFSET = 150;
    
    /**
     * Initialises the world with actual screen dimensions. Agent and circle are null.
     * @param windowSize 
     */
    public World(Dimension windowSize,ExpParam params) {
        gridSize = new Dimension((int)params.get("GRID_WIDTH"), (int)params.get("GRID_HEIGHT"));
    }
    
       
    
    /**
     * Initialises the world with circle horizontal displacement, x velocity and agent genotype.
     * @param circleDisp
     * @param circleDX
     * @param g 
     */
    public World(float circleDisp,float circleDX, AgentGenotype g,ExpParam params) {
        initAgent(g,params);
        initCircle(params,circleDisp, circleDX);
    }
    
    
    private void initCircle(ExpParam params, float circleDisp, float circleDX) {
        circle = new Circle(
                new Point2D.Float(
                        (int)params.get("GRID_CENTER") + circleDisp,
                        (int)params.get("CIRCLE_START_Y")
                ),
                (int)params.get("CIRCLE_RADIUS"),
                0,
                (int)params.get("GRID_WIDTH")
        );
        this.circleDX = circleDX;
    }
    
    private void initAgent(AgentGenotype g,ExpParam params) {
        agent = new Agent(
                new Point2D.Float((int)params.get("GRID_CENTER"),
                (int)params.get("AGENT_START_Y")),
                (int)params.get("AGENT_RADIUS"),
                g,
                params,
                BOUNDOFFSET,
                (int)params.get("GRID_WIDTH")-BOUNDOFFSET
        );
    }
    
    
    /**
     * Paints the grid, agent and circle at their current locations
     * @param g
     * @param windowSize 
     */
    public void paint(Graphics g, Dimension windowSize) {
        Graphics2D g2d = (Graphics2D)g;
        AffineTransform orig = g2d.getTransform();
        AffineTransform scaled = new AffineTransform();
        float zoomX,zoomY;
        
        zoomX = (float)windowSize.width / (float)gridSize.width;
        zoomY = (float)windowSize.height / (float)gridSize.height;
        scaled.translate(
                (windowSize.width / 2) - (gridSize.width * zoomX) / 2, 
                (windowSize.height / 2) - (gridSize.height * zoomY) / 2
        );
        scaled.scale(zoomX, zoomY);
        g2d.transform(scaled);
        if(agent != null)
            agent.draw(g2d);
        if(circle != null)
            circle.draw(g2d);
        
        g2d.setTransform(orig);
    }
    
    /**
     * Integrates the world 1 time step
     */
    public void step() {
        circle.move(circleDX,circleDY);
        agent.doCollisions(circle);
        agent.step();
    }
    
    public float getPredictionError() {
        return agent.getPredictionError();
    }
    
    /**
     * @return whether the circle is at or below the agent
     */
    public boolean circleReachedAgent() {
        return circle.getY() >= (agent.getY());
    }
    /**
     * @return the distance between circle and agent
     */
    public float getDistance() {
        return (float) Math.abs(circle.getX() - agent.getX());
    }
    
}
