/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mb459.easy.premca.sim.ctrnn;

import com.google.common.primitives.Ints;
import org.mb459.easy.premca.exp.ExpParam;
import org.mb459.easy.premca.genesis.AgentGenotype;
import java.util.Random;

/**
 *
 * @author Miles
 */
public class GMCTRNN extends CTRNN {

    public GMCTRNN(AgentGenotype g, ExpParam params) {
        super(g, params);
        stepSize = (float)(double)params.get("EULER_TIMESTEP");
        CTRNNLayout layout = g.layout;
        nPred = layout.predPairs.size();
        predPair1 = new int[nPred];
        predPair2 = new int[nPred];
        for(int i = 0; i < nPred; i++) {
            predPair1[i] = layout.predPairs.get(i).neuron1.ID;
            predPair2[i] = layout.predPairs.get(i).neuron2.ID;
        }
    }
    
    private final int predPair1[],predPair2[],nPred;
    
    int counter = 0;
    int newVelocity = 0;
    final float stepSize;

    @Override
    public void step(int[] inputs) {
        if(counter == 3) {
            newVelocity = (int) Math.round(getRandomMovement());
            counter = 0;
        } else
            counter++;        
        //MotorValues next = params.getNextMotorStep();
        states[0] -= newVelocity;
        states[1] += newVelocity;
        EulerStep(stepSize,inputs);
    }

    @Override
    public void EulerStep(float stepSize, int[] inputs) {
        for(int i = 0; i < n; i++) {
            float input;
            int sensInd = sensNeurIndices.indexOf(i);
            if(sensInd != -1)
                input = inputs[sensInd];
            else {
                int predInd = Ints.indexOf(predPair1, i);
                if(predInd != -1) {
                    float predError = (outputs[predPair1[predInd]] - outputs[predPair2[predInd]]);
                input = predError;
                } else
                input = 0;
            }
            for(int j = 0; j < n; j++)
                input += weights[j][i] * outputs[j];
            states[i] += stepSize * taus[i] * (input - states[i]);
            outputs[i] = sigmoid(gains[i] * (states[i] + biases[i]));
        }
    }
    
    
    public double getPredictionError() {
        double sum = 0;
        for(int i = 0; i < nPred; i++) {
            double error = (outputs[predPair1[i]] - outputs[predPair2[i]]);
            sum += Math.pow(error, 2);
        }
        return sum;
    }    
    
    private double getRandomMovement() {
        Random rand = new Random();
        //generate a random number in the range [-5,5]
        return rand.nextGaussian() * 2;
    }

    @Override
    public int getVelocity() {
        return (int)Math.round((outputs[outIndR] - outputs[outIndL]) * 5);
    }
    
    
    
}
