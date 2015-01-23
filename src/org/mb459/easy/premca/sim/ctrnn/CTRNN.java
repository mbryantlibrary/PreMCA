/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mb459.easy.premca.sim.ctrnn;

import org.mb459.easy.premca.genesis.AgentGenotype;
import org.mb459.easy.premca.exp.ExpParam;
import java.util.ArrayList;

/**
 * Simulates a CTRNN. Loads neurons into an array from a CTRNNLayout.
 * @author Miles
 */
public class CTRNN {
    
    int n; //number of neurons
    int nSensors; //number of sensory inputs in the agent. The first nSensor neurons will receive input
    public Neuron[] neurons; //array of neurons
    public ArrayList<ArrayList<Neuron>> neuronLayers; //stores layers of neurons
    public float[] taus,biases,gains,states,outputs;
    public float[][] weights;
    public int outIndL,outIndR;
    public ArrayList<Integer> sensNeurIndices;
    ExpParam params;
    
    /**
     * Creates a new CTRNN with specified number of sensors and genotype
     * @param nSensors
     * @param g 
     */
    public CTRNN(AgentGenotype g, ExpParam params) {
        this.n = g.layout.getTotalN(); //get number of neurons
        
        taus = new float[n];
        biases = new float[n];
        gains = new float[n];
        states = new float[n];
        outputs = new float[n];
        weights = new float[n][n];
        
        nSensors = g.layout.sensorInputs.size();
        
        
        sensNeurIndices = g.layout.sensorInputs;
        
        ArrayList<org.mb459.easy.premca.sim.ctrnn.Neuron> neurs = g.layout.getAllNeurons();
        for(int i = 0; i < n; i++) {
            org.mb459.easy.premca.sim.ctrnn.Neuron neuron = neurs.get(i);
            taus[i] = neuron.getMappedTau();
            gains[i] = neuron.getMappedGain();
            biases[i] = neuron.getMappedBias();
            for(int j = 0; j < n; j++) {
                if(neuron.conns.contains(j)) {
                    weights[i][j] = neuron.getMappedWeights().get(neuron.conns.indexOf(j));
                } else
                    weights[i][j] = 0;
            }
            states[i] = 0.5f;
            outputs[i] = 0f;
        }
        outIndL = g.layout.getMotorNeuron(true).ID;
        outIndR = g.layout.getMotorNeuron(false).ID;
        
        this.params = params;
    }
    
    /**
     * Integrates one time step
     * @param inputs 
     */
    public void step(int[] inputs) {
        float stepSize = (float)(double)params.get("EULER_TIMESTEP");
        EulerStep(stepSize,inputs);
    }
    
    /**
     * Integrates one step using Euler's method (see any CTRNN resource)
     * @param stepSize
     * @param inputs 
     */
    public void EulerStep(float stepSize, int[] inputs) {
        for(int i = 0; i < n; i++) {
            float input;
            int sensInd = sensNeurIndices.indexOf(i);
            if(sensInd != -1)
                input = inputs[sensInd];
            else
                input = 0;
            for(int j = 0; j < n; j++)
                input += weights[j][i] * outputs[j];
            states[i] += stepSize * taus[i] * (input - states[i]);
            outputs[i] = sigmoid(gains[i] * (states[i] + biases[i]));
        }
    }
    /**
     * @return motor output: relative difference between motor neurons (last two neurons)
     */
    public int getVelocity() {
        return (int)Math.round((outputs[outIndR] - outputs[outIndL]) * 5);
    }
    /**
     * Standard sigmoid function.
     * @param x
     * @return 
     */
    protected float sigmoid(float x) {
        return 1.0f / (1 + (float)Math.exp(-x));
    }
    /**
     * Inverse standard sigmoid function.
     * @param x
     * @return 
     */
    private float invSigmoid(float x) {
        return (float)Math.log(x / (1 - x));
    }
    
}
