/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mb459.easy.premca.genesis;

import java.util.ArrayList;
import org.junit.Test;
import static org.junit.Assert.*;
import org.mb459.easy.premca.exp.ExpParam;
import org.mb459.easy.premca.sim.ctrnn.CTRNNLayout;

/**
 *
 * @author Miles
 */
public class StatsTest {
    
    public StatsTest() {
    }

    @Test
    public void StatsWithDummyPopulationShouldReturnMaxFitnessOf11_5() {
        Stats stats = new Stats(1,new DummyPopulation());
        assertEquals(11.5f, stats.maxFit, 0.001);
    }
    
    @Test
    public void StatsWithDummyPopulationShouldReturnMinFitnessOf2_5() {
        Stats stats = new Stats(1,new DummyPopulation());
        assertEquals(2.5f, stats.minFit, 0.001);
    }
    
    @Test
    public void StatsWithDummyPopulationShouldReturnAvgFitnessOf7() {
        Stats stats = new Stats(1,new DummyPopulation());
        assertEquals(7f, stats.avgFit, 0.001);
    }
    
    public class DummyPopulation extends Population {

        
        
        @Override
        public int size() {
            return 10; //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        ArrayList<Float> getFitnesses() {
            ArrayList<Float> fitnesses = new ArrayList<>(10);
            fitnesses.add(2.5f);
            fitnesses.add(3.5f);
            fitnesses.add(4.5f);
            fitnesses.add(5.5f);
            fitnesses.add(6.5f);
            fitnesses.add(7.5f);
            fitnesses.add(8.5f);
            fitnesses.add(9.5f);
            fitnesses.add(10.5f);
            fitnesses.add(11.5f);
            
            return fitnesses; //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public AgentIndividual getIndividual(int index) {
            return new AgentIndividual(new CTRNNLayout(), new ExpParam()); //To change body of generated methods, choose Tools | Templates.
        }
        
        
        
    }
    
    
}
