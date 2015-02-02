package org.mb459.easy.premca.sim;

import org.mb459.easy.premca.genesis.AgentGenotype;
import org.mb459.easy.premca.exp.ExpParam;
import org.mb459.easy.premca.sim.ctrnn.GMCTRNN;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Logger;
import org.mb459.easy.premca.sim.ctrnn.CTRNN;
import org.mb459.easy.premca.sim.ctrnn.CTRNNLayout;

/**
 * Represents a minimally cognitive agent with a CTRNN and two motor inputs.
 *
 * @author Miles
 */
public class Agent extends Circle {

    private static final Logger LOG = Logger.getLogger(Agent.class.getName());

    private final ArrayList<Sensor> sensors = new ArrayList<>();
    private final Point2D.Float sensorBase = new Point2D.Float(); //point the sensors extend from

    private final int nSensors;
    private final CTRNN nn;
    private final CTRNNLayout layout;

    public CTRNNLayout getLayout() {
        return layout;
    }
    
    public ArrayList<Float> getLogRow() {
        ArrayList<Float> row = new ArrayList<>();
        for(int i = 0; i < getLayout().getTotalN(); i++) {
            row.add(nn.states[i]);
            row.add(nn.outputs[i]);
        }
        return row;
    }
    
    /**
     * Initialises a new agent with a specified location, radius and genotype
     *
     * @param location
     * @param radius
     * @param g
     * @param params
     * @param leftBound
     * @param rightBound
     */
    public Agent(Point2D.Float location, int radius, AgentGenotype g, ExpParam params, int leftBound, int rightBound) {
        super(location, radius, leftBound, rightBound);

        sensorBase.x = location.x; //place in middle
        sensorBase.y = location.y - radius;
        nSensors = g.getNSensors();
        createSensors(nSensors, (int) params.get("AGENT_SENSOR_LENGTH"), (float) params.get("AGENT_EYE_WIDTH_RAD"));
        nn = new GMCTRNN(g, params);
        layout = g.layout;
    }

    /**
     * creates the sensors on the agent according to ExpParam
     */
    private void createSensors(int n, int sensorLength, float eye_width) {
        float left = 0.5f + 0.5f * eye_width;
        for (float i = 0; i < n; i++) //loop through sensors
        {
            sensors.add(new Sensor(sensorBase, left - (i / ((n - 1.0f) * (1.0f / eye_width))), sensorLength));
        }
    }

    /**
     * integrates one time step and moves agent according to motor outputs
     */
    public void step() {
        // get sensory inputs and step neural net with them
        int[] inputs = new int[nSensors];
        for (int i = 0; i < nSensors; i++) {
            inputs[i] = sensors.get(i).getAct();
        }
        nn.step(inputs);
        if (circle.getX() > lBound & circle.getX() < rBound - radius) // move if not at edge of grid
        {
            move(nn.getVelocity());
        }
    }

    private boolean loggingEnabled = false;

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
        this.loggingEnabled = loggingEnabled;
        if (loggingEnabled) {
            loggingData = new ArrayList<>();
        } else {
            loggingData = null;
        }
    }

    private ArrayList<ArrayList<Double>> loggingData;

   public ArrayList<ArrayList<Double>> getLoggingDataArray() {
       return loggingData;
   }

    /**
     * moves the agent x distance horizontally
     *
     * @param x
     */
    private void move(int x) {
        circle.setFrameFromCenter(circle.getCenterX() + x, circle.getCenterY(), circle.getX() + x, circle.getY());
        for (Sensor s : sensors) {
            s.move(x);
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        super.draw(g2);
        for (Sensor s : sensors) {
            s.draw(g2); //draw each sensor
        }
    }

    /**
     * sets sensor activations based on circle location
     *
     * @param circle
     */
    public void doCollisions(Circle circle) {
        for (Sensor s : sensors) {
            s.doCollision(circle);
        }
    }

    float getPredictionError() {
        return (float) ((GMCTRNN) nn).getPredictionError();
    }

    Collection<? extends String> getTimestepDataTitles() {
       ArrayList<String> titles = new ArrayList<>();
        for(int i = 0; i < nn.outputs.length; i++) {
            titles.add("states_nn" + i);
            titles.add("output_nn" + i);
        }
        return titles;
    }

}
