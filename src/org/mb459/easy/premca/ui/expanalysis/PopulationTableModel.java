/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mb459.easy.premca.ui.expanalysis;

import au.com.bytecode.opencsv.CSVReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.AbstractTableModel;
import org.mb459.easy.premca.sim.ctrnn.CTRNNLayout;

/**
 *
 * @author Miles
 */
public class PopulationTableModel extends AbstractTableModel {

    public PopulationTableModel(CTRNNLayout layout) {
        this.layout = layout;
    }
    
    private final CTRNNLayout layout ;

    /**
     * Get the value of layout
     *
     * @return the value of layout
     */
    public CTRNNLayout getLayout() {
        return layout;
    }

    
    public class DataIndividual {
        public int ID;
        public float fitness;
        public ArrayList<Float> genes = new ArrayList<>();
    }

    private ArrayList<DataIndividual> data = new ArrayList<>();
    private int nGenes = 0;
    
    
    public DataIndividual getInd(int row) {
        return data.get(row);
    }
    
    public void loadFromCSV(String filename) {
        try {
            CSVReader reader = new CSVReader(new FileReader(filename));
            reader.readNext();
            List<String[]> entries = reader.readAll();
            for(String[] strarr : entries) {
                DataIndividual ind = new DataIndividual();
                ind.ID = Integer.parseInt(strarr[0]);
                ind.fitness = Float.parseFloat(strarr[1]);
                for(int i = 2; i < strarr.length; i++) {
                    ind.genes.add(Float.parseFloat(strarr[i]));
                }                
                data.add(ind);
            }
            nGenes = data.get(0).genes.size();
            fireTableStructureChanged();
        } catch (IOException ex) {
            Logger.getLogger(PopulationTableModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return nGenes + 2;
    }

    @Override
    public Object getValueAt(int row, int col) {
        if(col == 0) {
            return data.get(row).ID;
        } else if (col == 1) {
            return data.get(row).fitness;
        } else if (col > 1) {
            return data.get(row).genes.get(col - 2);
        }
        return null;
    }

    @Override
    public String getColumnName(int col) {
        if(col == 0) {
            return "ID";
        } else if (col == 1) {
            return "Fitness";
        } else if (col > 1) {
            return "Gene " + (col-2);
        }
        return null;
    }
    
    
    
}
