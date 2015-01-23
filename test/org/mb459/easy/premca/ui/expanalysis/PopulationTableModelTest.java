/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mb459.easy.premca.ui.expanalysis;

import javax.swing.event.TableModelListener;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Miles
 */
public class PopulationTableModelTest {
    
    public PopulationTableModelTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Test of loadFromCSV method, of class PopulationTableModel.
     */
    @Test
    public void testLoadFromCSV() {
        System.out.println("loadFromCSV");
        String filename = System.getProperty("user.dir") + "\\population-1.csv";
        PopulationTableModel instance = new PopulationTableModel();
        instance.loadFromCSV(filename);
    }

    /**
     * Test of getRowCount method, of class PopulationTableModel.
     */
    @Test
    public void testGetRowCount() {
        System.out.println("getRowCount");
        PopulationTableModel instance = new PopulationTableModel();
        int expResult = 0;
        int result = instance.getRowCount();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getColumnCount method, of class PopulationTableModel.
     */
    @Test
    public void testGetColumnCount() {
        System.out.println("getColumnCount");
        PopulationTableModel instance = new PopulationTableModel();
        int expResult = 0;
        int result = instance.getColumnCount();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getColumnName method, of class PopulationTableModel.
     */
    @Test
    public void testGetColumnName() {
        System.out.println("getColumnName");
        int columnIndex = 0;
        PopulationTableModel instance = new PopulationTableModel();
        String expResult = "";
        String result = instance.getColumnName(columnIndex);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getColumnClass method, of class PopulationTableModel.
     */
    @Test
    public void testGetColumnClass() {
        System.out.println("getColumnClass");
        int columnIndex = 0;
        PopulationTableModel instance = new PopulationTableModel();
        Class expResult = null;
        Class result = instance.getColumnClass(columnIndex);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isCellEditable method, of class PopulationTableModel.
     */
    @Test
    public void testIsCellEditable() {
        System.out.println("isCellEditable");
        int rowIndex = 0;
        int columnIndex = 0;
        PopulationTableModel instance = new PopulationTableModel();
        boolean expResult = false;
        boolean result = instance.isCellEditable(rowIndex, columnIndex);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getValueAt method, of class PopulationTableModel.
     */
    @Test
    public void testGetValueAt() {
        System.out.println("getValueAt");
        int rowIndex = 0;
        int columnIndex = 0;
        PopulationTableModel instance = new PopulationTableModel();
        Object expResult = null;
        Object result = instance.getValueAt(rowIndex, columnIndex);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setValueAt method, of class PopulationTableModel.
     */
    @Test
    public void testSetValueAt() {
        System.out.println("setValueAt");
        Object aValue = null;
        int rowIndex = 0;
        int columnIndex = 0;
        PopulationTableModel instance = new PopulationTableModel();
        instance.setValueAt(aValue, rowIndex, columnIndex);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addTableModelListener method, of class PopulationTableModel.
     */
    @Test
    public void testAddTableModelListener() {
        System.out.println("addTableModelListener");
        TableModelListener l = null;
        PopulationTableModel instance = new PopulationTableModel();
        instance.addTableModelListener(l);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of removeTableModelListener method, of class PopulationTableModel.
     */
    @Test
    public void testRemoveTableModelListener() {
        System.out.println("removeTableModelListener");
        TableModelListener l = null;
        PopulationTableModel instance = new PopulationTableModel();
        instance.removeTableModelListener(l);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
