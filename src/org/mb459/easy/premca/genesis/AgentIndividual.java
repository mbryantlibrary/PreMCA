/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mb459.easy.premca.genesis;

import org.mb459.easy.premca.exp.Trial;
import org.mb459.easy.premca.exp.ExpParam;
import org.mb459.easy.premca.sim.ctrnn.CTRNNLayout;
import java.util.Random;
import org.mb459.easy.premca.ui.expanalysis.PopulationTableModel;

/**
 *
 * @author Miles
 */
public class AgentIndividual implements Comparable<AgentIndividual> {
    
    public static AgentIndividual fromDataIndividual(PopulationTableModel.DataIndividual ind, ExpParam params, CTRNNLayout layout) {
        AgentIndividual agInd = new AgentIndividual(layout, params);
        return agInd;
    }
    
    
    protected AgentGenotype genotype;
    protected ExpParam params;
    protected int nTrials;

    public AgentIndividual(CTRNNLayout layout,ExpParam params) {
        genotype = new AgentGenotype(layout);
        this.params = params;
        nTrials = (int)params.get("N_SCRIPTED_RUNS");
    }
    
    
    public AgentGenotype getGenotype() {
        return genotype;
    }
    

    void mutate(float p_mutation) {
        genotype.mutate(p_mutation);
        rawFitness = calcRawFitness();
    }

    private float rawFitness = 0.0f;
    private boolean rawFitnessSet = false;
    
    
    public float rawFitness() {
        if(!rawFitnessSet) {
            rawFitness = calcRawFitness();
            rawFitnessSet = true;
        }
        return rawFitness;
    }
    
    
    
    public float calcRawFitness() {
        float sum = 0.0f;
        
        float dispRange = (float)params.get("FITNESS_EVAL_CIRC_DISP");
        float angRange = -(float)params.get("FITNESS_EVAL_H_VELOCITY");
        int nTrials = (int)params.get("FITNESS_EVAL_NTRIALS");
        float dHVelo = ((float)params.get("FITNESS_EVAL_H_VELOCITY") * 2.0f) / (nTrials - 1);
        float distClip = dispRange;
        
        //float[] angs = {1,-3,-2,-4,4,-6,5,-5,-1,2,6,3};
        
        for(int i = 0; i < nTrials; i++) {
            float disp = (i * (2 * dispRange / (nTrials - 1)) - dispRange);
            //float ang = (int)Math.round(angRange + (double)(i * dHVelo));
            
            Trial t = new Trial(0,0,genotype,params);
            float fitness = t.run();
            if(fitness < 0)
                fitness = 0.0f;
            if(fitness > distClip)
                fitness = distClip;
            sum += fitness;
        }
        
        return (200 - (sum / nTrials)) / 200;
    }
    
 
    
    public void crossover(float pCross, AgentIndividual ind) {
        
        Random rand = new Random();
        for(int i = 0; i< genotype.genes.length; i++) {
            if(rand.nextFloat() < pCross)
                genotype.genes[i] = ind.genotype.genes[i];
        }
        
        rawFitness = calcRawFitness();
    }

    @Override
    public int compareTo(AgentIndividual o) {
        return Float.valueOf(this.rawFitness()).compareTo(Float.valueOf(o.rawFitness()));
    }
    
}
