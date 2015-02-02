/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mb459.easy.premca.sim;

import com.google.common.base.Joiner;
import org.mb459.easy.premca.genesis.AgentGenotype;
import org.mb459.easy.premca.exp.ExpParam;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.logging.Logger;
import org.mb459.easy.premca.sim.ctrnn.Neuron;

/**
 * Represents and integrates the world of a single trial.
 * @author Miles
 */
public class World {
    private static final Logger LOG = Logger.getLogger(World.class.getName());
    
    private Agent agent; 
    private Circle circle;
    private Dimension gridSize = new Dimension(400,275);
    private float circleDX = 0; //circle horizontal velocity
    private final float circleDY = 3; //circle vertical velocity
    private final int BOUNDOFFSET = 150;
    
    private boolean loggingEnabled = false;
    private ArrayList<ArrayList<Float>> loggingData;

    /**
     * Get the value of loggingEnabled
     *
     * @return the value of loggingEnabled
     */
    public boolean isLoggingEnabled() {
        return loggingEnabled;
    }

    /**
     * Set the value of loggingEnabled
     *
     * @param loggingEnabled new value of loggingEnabled
     */
    public void setLoggingEnabled(boolean loggingEnabled) {
        if(loggingEnabled) {
            loggingData = new ArrayList<>();
        } else {
            loggingData = null;
        }
        this.loggingEnabled = loggingEnabled;
    }

    public String getLoggingData() {
        if(!isLoggingEnabled() | loggingData == null) {
            LOG.info("Logging data requested but logging not available/logging data is null; returning empty string");
            return "";
        } 
        StringBuilder builder = new StringBuilder();
        Joiner csvJoiner = Joiner.on(",");
        ArrayList<String> titles = new ArrayList<>();
        titles.add("Circle_X");titles.add("Circle_Y");titles.add("Agent_X");
        titles.addAll(agent.getTimestepDataTitles());
        
        builder.append(csvJoiner.join(titles));
        builder.append("\n");
        
        for(ArrayList<Float> dataRow : loggingData) {
            builder.append(csvJoiner.join(dataRow));
            builder.append("\n");
        }
        
        return builder.toString();
    }
    
    /**
     * Initialises the world with actual screen dimensions. Agent and circle are null.
     * @param windowSize 
     */
    public World(Dimension windowSize,ExpParam params) {
        gridSize = new Dimension((int)params.get("GRID_WIDTH"), (int)params.get("GRID_HEIGHT"));
    }
    
    private ArrayList<Float> getNextLogRow() {
        ArrayList<Float> row = new ArrayList<>();
        row.add(circle.getCenterX());
        row.add(circle.getCenterY());
        row.add(agent.getCenterX());
        row.addAll(agent.getLogRow());
        return row;
    }
    
    private ArrayList<String> getLoggingHeaderRowTitles() {
        ArrayList<String> row = new ArrayList<>();
        row.add("Circle X");
        row.add("Circle Y");
        row.add("Agent X");
        for(Neuron neuron : agent.getLayout().getAllNeurons()) {
            row.add("N" + neuron.ID + "_state");
            row.add("N" + neuron.ID + "_output");
        }
        return row;
    }

    ArrayList<String> loggingHeaderRow;

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
        if(isLoggingEnabled() & loggingData != null) {
            loggingData.add(getNextLogRow());
        }
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
