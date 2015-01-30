/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mb459.easy.premca.genesis;

import org.mb459.easy.premca.sim.ctrnn.CTRNNLayout;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Random;
import org.mb459.easy.premca.ui.expanalysis.PopulationTableModel;

/**
 *
 * @author Miles
 */
public class AgentGenotype implements Serializable{
    
    public CTRNNLayout layout = new CTRNNLayout();
    boolean GENOME_CHANGED = false;
    
    //private int nWeights;
    public int len;
    public float[] genes;
    int n;
    private float fitness = 0.0f;
    private String name = "";
    private String filename = "";
    private Random rand = new Random();
    
    public static AgentGenotype fromDataIndividual(PopulationTableModel.DataIndividual ind, CTRNNLayout layout) {
        AgentGenotype geno = new AgentGenotype(layout);
        for(int i = 0; i < ind.genes.size(); i++) {
            geno.genes[i] = ind.genes.get(i);
        }
        return geno;
    }

    public AgentGenotype(CTRNNLayout layout) {
        this.layout = layout;
        len = layout.getGenomeLength();
        n = layout.getTotalN();
        randomiseGenome();
        updateLayout();
    }
    
    private void randomiseGenome() {
        genes = new float[len];
        for(int i = 0; i < len; i++)
            genes[i] = rand();
    }
    
    public void updateLayout() {
        layout.update(genes);
    }
    
    
    
    public AgentGenotype(String filename) throws IOException, ClassNotFoundException {
        FileInputStream fs = new FileInputStream(filename);
        ObjectInputStream os = new ObjectInputStream(fs);
        AgentGenotype g = (AgentGenotype)os.readObject();
        os.close();fs.close();
        copyFrom(g);
        this.filename = filename;
    }
    
    
    private final void copyFrom(AgentGenotype g) {
        genes = g.genes;
        setFitness(g.getFitness());
        setName(g.getName());
    }
    
    
    private float rand() {
        return (float)rand.nextDouble() * 2 - 1;
    }
    
    
    
    private float randCreep(float mutVariance) {
        return (float)rand.nextGaussian() * mutVariance;
    }
    
    
    public void mutate(float mutVariance) {
        for(float g : genes) {
            float mut,dm;
            dm = randCreep(mutVariance);
            mut = g + dm;
            if(mut < -1.0)
                g = -2 - mut;
            else if(mut > 1.0)
                g = 2 - mut;
            else
                g = mut;
        }
        updateLayout();
    }
    
    public void saveToFile(String filename) throws FileNotFoundException, IOException {
        FileOutputStream fs = new FileOutputStream(filename);
        ObjectOutputStream os = new ObjectOutputStream(fs);
        os.writeObject(this);
        os.close();fs.close();
    }
    
    public int getNSensors() {
        return layout.sensorInputs.size();
    }

    /**
     * @return the fitness
     */
    public float getFitness() {
        return fitness;
    }

    /**
     * @param fitness the fitness to set
     */
    public void setFitness(float fitness) {
        this.fitness = fitness;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the filename
     */
    public String getFilename() {
        return filename;
    }
    
    @Override
    public String toString() {
        String str = "";
        for(float gene : genes) {
            String prefix = gene > 0 ? "+" : "";
            str += prefix + String.format("%f\t", gene);
        }
        return str;
    }

}
