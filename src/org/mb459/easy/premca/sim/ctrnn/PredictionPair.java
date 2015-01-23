/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mb459.easy.premca.sim.ctrnn;

/**
 *
 * @author Miles
 */
public class PredictionPair {
    
    Neuron neuron1;
    Neuron neuron2;

    public Neuron getNeuron1() {
        return neuron1;
    }

    public void setNeuron1(Neuron neuron1) {
        this.neuron1 = neuron1;
    }
    
    public void addNeuron(Neuron neuron) {
        if(neuron1 == null) {
            neuron1 = neuron;
            return;
        } else if(neuron2 == null) {
            neuron2 = neuron;
            return;
        }
        System.err.println("Error: two neurons already assigned to this prediction pair");        
    }

    public Neuron getNeuron2() {
        return neuron2;
    }

    public void setNeuron2(Neuron neuron2) {
        this.neuron2 = neuron2;
    }
    

    public PredictionPair() {
    }
    
}
