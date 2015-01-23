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
     * Test of getFitnesses method, of class Population.
     */
    @Test
    public void testGetFitnesses() {
        System.out.println("getFitnesses");
        Population instance = new Population();
        ArrayList<Float> expResult = null;
        ArrayList<Float> result = instance.getFitnesses();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getGenotypes method, of class Population.
     */
    @Test
    public void testGetGenotypes() {
        System.out.println("getGenotypes");
        Population instance = new Population();
        ArrayList<String> expResult = null;
        ArrayList<String> result = instance.getGenotypes();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getRandIndex method, of class Population.
     */
    @Test
    public void testGetRandIndex() {
        System.out.println("getRandIndex");
        Population instance = new Population();
        int expResult = 0;
        int result = instance.getRandIndex();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of size method, of class Population.
     */
    @Test
    public void testSize() {
        System.out.println("size");
        Population instance = new Population();
        int expResult = 0;
        int result = instance.size();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isABetterThanB method, of class Population.
     */
    @Test
    public void testIsABetterThanB() {
        System.out.println("isABetterThanB");
        int a = 0;
        int b = 0;
        Population instance = new Population();
        boolean expResult = false;
        boolean result = instance.isABetterThanB(a, b);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of crossover method, of class Population.
     */
    @Test
    public void testCrossover() {
        System.out.println("crossover");
        int a = 0;
        int b = 0;
        Population instance = new Population();
        instance.crossover(a, b);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of mutate method, of class Population.
     */
    @Test
    public void testMutate() {
        System.out.println("mutate");
        int a = 0;
        Population instance = new Population();
        instance.mutate(a);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
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

    /**
     * Test of toString method, of class Population.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        Population instance = new Population();
        String expResult = "";
        String result = instance.toString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
