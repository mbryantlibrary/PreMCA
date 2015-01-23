/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mb459.easy.premca.genesis;

import org.mb459.easy.premca.exp.ExpParam;
import org.mb459.easy.premca.sim.ctrnn.CTRNNLayout;
import org.mb459.easy.premca.exp.Trial;

/**
 *
 * @author Miles
 */
public class GMIndividual extends AgentIndividual {

    public GMIndividual(CTRNNLayout layout, ExpParam params) {
        super(layout, params);
    }

    @Override
    public float calcRawFitness() {
        float sum = 0.0f;
        
        for(int i = 0; i < nTrials; i++) {
            Trial t = new Trial(0,0,genotype,params);
            float fitness = t.run();
            sum += fitness;
        }
        
        return 1 - (sum / nTrials);
        
    }
    
        
    
}
