/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mb459.easy.premca.genesis;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.mb459.easy.premca.exp.ExpParam;
import org.mb459.easy.premca.sim.ctrnn.CTRNNLayout;

/**
 *
 * @author Miles
 */
public class PopulationTest {
    
    public PopulationTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }

    
    /**
     * Test of saveToCSV method, of class Population.
     */
    @Test
    public void testSaveToCSV() {
        System.out.println("saveToCSV");
        ExpParam param = new ExpParam();
        CTRNNLayout layout = new CTRNNLayout();
        try {
            layout = CTRNNLayout.fromJSONFile(System.getProperty("user.dir") + "\\data\\CompDynamicsGMNN.nnl");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PopulationTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        String filename = System.getProperty("user.dir") + "\\testPopSaveToCSV.csv";
        Population instance = new Population(
            (int)param.get("GA_NPOP"),
            ((Number)param.get("GA_P_MUT")).floatValue(),
            ((Number)param.get("GA_CROSSOVER_P_CROSS")).floatValue(), 
            param,
            layout
        );
        instance.saveToCSV(filename);
    }

    
}
